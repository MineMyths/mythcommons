package me.omega.mythcommons.command

import me.omega.mythcommons.hasPermissionCountLevel
import me.omega.mythcommons.messenger.MessageType
import me.omega.mythcommons.messenger.Messenger
import net.minestom.server.command.CommandSender
import net.minestom.server.command.ConsoleSender
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.suggestion.Suggestion
import net.minestom.server.command.builder.suggestion.SuggestionEntry
import net.minestom.server.entity.Player

fun CommandSender.isPlayer(sendMessage: Boolean = true): Boolean {
    val isPlayer = this is Player
    if (!isPlayer && sendMessage) {
        Messenger.send(this, MessageType.ERROR, "You must be a player to use this command.")
    }
    return isPlayer
}
fun CommandSender.isConsole(sendMessage: Boolean = true): Boolean {
    val isConsole = this is ConsoleSender
    if (!isConsole && sendMessage) {
        Messenger.send(this, MessageType.ERROR, "You must be the console to use this command.")
    }
    return isConsole
}

fun CommandSender.checkPermission(vararg permission: String, sendMessage: Boolean = true): Boolean {
    if (isConsole(false)) return true
    for (perm in permission) {
        val hasPermission = this.hasPermission(perm) || (this as Player).hasPermissionCountLevel(perm)
        if (!hasPermission) {
            if (sendMessage) Messenger.send(this, MessageType.ERROR, "You need the permission <white>%s</white> to use this command.", perm)
            return false
        }
    }
    return true
}

fun CommandContext.addSuggestion(input: String, suggestion: Suggestion): Boolean {
    if (input.contains(this.input.split(" ").last(), true)) {
        suggestion.addEntry(SuggestionEntry(input))
        return true
    }
    return false
}