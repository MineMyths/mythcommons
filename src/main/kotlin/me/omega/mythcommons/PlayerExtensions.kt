package me.omega.mythcommons

import net.minestom.server.entity.Player

fun Player.hasPermissionCountLevel(permission: String): Boolean {
    return this.permissionLevel >= 4 || this.hasPermission(permission)
}