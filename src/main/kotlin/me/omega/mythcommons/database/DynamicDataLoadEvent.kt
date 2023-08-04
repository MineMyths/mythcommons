package me.omega.mythcommons.database

import me.omega.mythcommons.database.DatabaseLoadCallback
import net.minestom.server.event.Event

class DynamicDataLoadEvent<K : Any, V : DynamicData>(val key: K, val callback: DatabaseLoadCallback<V>) : Event
