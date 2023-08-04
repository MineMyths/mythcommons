package me.omega.mythcommons.database

import me.omega.mythcommons.database.DatabaseLoadResult
import me.omega.mythcommons.database.DynamicData

data class DatabaseLoadCallback<V : DynamicData>(val result: DatabaseLoadResult, val value: V?)
