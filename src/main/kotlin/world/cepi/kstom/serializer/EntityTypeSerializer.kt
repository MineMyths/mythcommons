package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encoding.Decoder
import net.minestom.server.entity.EntityType

@OptIn(ExperimentalSerializationApi::class)
object EntityTypeSerializer : AbstractProtocolObjectSerializer<EntityType>(EntityType::class) {
    override fun deserialize(decoder: Decoder): EntityType = EntityType.fromNamespaceId(decoder.decodeString())
}