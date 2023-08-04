package me.omega.mythcommons

import net.kyori.adventure.text.Component
import world.cepi.kstom.adventure.asLegacyComponent
import java.util.*

fun String.capitalizeWords(): String {
    return this.lowercase().split(" ").joinToString(" ") {
        it.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}

fun String.wrap(maxChars: Int, prefix: String): List<String> {
    val words = this.split(" ")
    val lines = mutableListOf<String>()
    var line = prefix

    for (word in words) {
        if (line.length + word.length > maxChars) {
            lines.add(line.trim())
            line = prefix
        }
        line += "$word "
    }
    lines.add(line.trim())
    return lines
}

fun toLegacyComponent(strings: List<String>): Array<Component> {
    val list = mutableListOf<Component>()
    for (string in strings) {
        list.add(string.asLegacyComponent())
    }
    return list.toTypedArray()
}

fun String.wrapToLegacyComponent(maxChars: Int, prefix: String): Array<Component> {
    return toLegacyComponent(wrap(maxChars, prefix))
}
