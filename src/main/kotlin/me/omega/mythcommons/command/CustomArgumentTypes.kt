package me.omega.mythcommons.command

import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.Manager

object CustomArgumentTypes {

    val player = ArgumentType.Word("target")
        .setSuggestionCallback { _, context, suggestion ->
            val players = Manager.connection.onlinePlayers
            players.forEach { player ->
                context.addSuggestion(player.username, suggestion)
            }
        }

}