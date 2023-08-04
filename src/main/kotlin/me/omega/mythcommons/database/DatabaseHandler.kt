package me.omega.mythcommons.database

import com.mongodb.client.model.*
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import me.omega.mythcommons.database.DatabaseLoadCallback
import net.minestom.server.event.EventDispatcher
import org.bson.Document
import org.slf4j.Logger
import java.util.*
import kotlin.reflect.KClass

abstract class StaticDataHandler<K : Any, V : DynamicData>(
    private var collectionName: String,
    private var database: MongoDatabase,
    private var clazz: KClass<V>,
    val logger: Logger
) {
    var data: MutableMap<K, V> = HashMap<K, V>()
    lateinit var collection: MongoCollection<Document>

    val job = Job()
    val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    /**
     * This method is called after the value is loaded from the database.
     */
    abstract fun postLoad(key: K, value: V)

    /**
     * Gets the key from the specified value.
     */
    abstract fun getKey(value: V): K

    abstract fun serialize(value: V): Document

    abstract fun deserialize(document: Document): V?

    suspend fun init(): StaticDataHandler<K, V> {
        this.collection = database.getCollection(collectionName)
        this.collection.createIndex(Document("_id", 1))
        load(null)
        return this
    }

    operator fun get(key: K): Optional<V> {
        return Optional.ofNullable<V>(data[key])
    }

    open suspend fun load(key: K?): DatabaseLoadCallback<V> {
        try {
            val found = collection.find()
            val tempData = HashMap<K, V>()
            found.collect {
                val deserialized = deserialize(it)
                if (deserialized == null) {
                    logger.error("Failed to deserialize ${it.toJson()}")
                    return@collect
                }
                val iteratedKey = getKey(deserialized)
                postLoad(iteratedKey, deserialized)
                tempData[iteratedKey] = deserialized
            }
            data = tempData
            return DatabaseLoadCallback(DatabaseLoadResult.FOUND, null)
        } catch (e: Exception) {
            logger.error("Failed to load data from database.", e)
            return DatabaseLoadCallback(DatabaseLoadResult.NOT_FOUND, null)
        }
    }

    suspend fun save(key: K) {
        data[key]?.let { collection.replaceOne(Filters.eq("_id", key), serialize(it), ReplaceOptions().upsert(true)) }
    }

    suspend fun saveAll() {
        if (data.isNotEmpty()) {
            val bulkOperations: MutableList<WriteModel<Document>> = ArrayList<WriteModel<Document>>()
            for (key in data.keys) {
                val upsertOperation: WriteModel<Document> = UpdateOneModel(
                    Filters.eq("_id", key), Document(
                        "\$set",
                        serialize(data[key]!!)
                    ), UpdateOptions().upsert(true)
                )
                bulkOperations.add(upsertOperation)
            }
            collection.bulkWrite(bulkOperations)
        }
    }

    suspend fun delete(key: K) {
        data.remove(key)
        collection.deleteOne(Filters.eq("_id", key))
    }
}

abstract class DynamicDataHandler<K : Any, V : DynamicData>(
    collectionName: String,
    database: MongoDatabase,
    clazz: KClass<V>,
    logger: Logger
) : StaticDataHandler<K, V>(collectionName, database, clazz, logger) {

    /**
     * Gets a new value to be put in the database. Only puts this value in if [insertValueWhenMissing] is true.
     */
    abstract fun getNewValue(key: K): V

    /**
     * Whether to insert the value when it is missing from the database.
     */
    abstract fun insertValueWhenMissing(): Boolean

    override suspend fun load(key: K?): DatabaseLoadCallback<V> {
        if (key == null) {
            return DatabaseLoadCallback(DatabaseLoadResult.NOT_FOUND, null)
        }
        val now = System.currentTimeMillis()
        logger.info("Loading $key from database.")
        return try {
            val document = collection.find(Filters.eq("_id", key)).first()
            val value = deserialize(document)
            if (value != null) {
                data[key] = value
                postLoad(key, value)
            } else {
                logger.error("Failed to deserialize ${document.toJson()}")
                return DatabaseLoadCallback(DatabaseLoadResult.NOT_FOUND, null)
            }
            logger.info("Loaded $key from database in ${System.currentTimeMillis() - now}ms.")
            val callback = DatabaseLoadCallback(DatabaseLoadResult.FOUND, value)
            EventDispatcher.call(DynamicDataLoadEvent(key, callback))
            callback
        } catch (e: Exception) {
            val newValue = getNewValue(key)
            if (insertValueWhenMissing()) {
                data[key] = newValue
                save(key)
                postLoad(key, newValue)
            }
            logger.info("Loaded $key from database in ${System.currentTimeMillis() - now}ms.")
            val callback = DatabaseLoadCallback(DatabaseLoadResult.NOT_FOUND, newValue)
            EventDispatcher.call(DynamicDataLoadEvent(key, callback))
            callback
        }
    }

    suspend fun unload(key: K) {
        EventDispatcher.call(DynamicDataUnloadEvent(key, data[key]))
        val now = System.currentTimeMillis()
        logger.info("Unloading $key from memory.")
        save(key)
        data.remove(key)
        logger.info("Unloaded $key from memory in ${System.currentTimeMillis() - now}ms.")
    }
}

