package me.omega.mythcommons.database

import net.minestom.server.event.Event

class DynamicDataUnloadEvent<K : Any, V : DynamicData>(val key: K, val data: V?) : Event
