package me.omega.mythcommons.command

import net.minestom.server.command.builder.Command
import kotlin.reflect.KClass

annotation class Subcommand(val clazz: KClass<out Command>)
