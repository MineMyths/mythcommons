package me.omega.mythcommons.messenger

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.minimessage.MiniMessage

object Messenger {

    fun send(audience: Audience, message: String, vararg replacements: Any) =
        audience.sendMessage(MiniMessage.miniMessage().deserialize(String.format(message, *replacements)))

    fun send(audience: Audience, type: MessageType, message: String, vararg replacements: Any) =
        send(audience, type, message, *replacements, playSound = true)

    fun send(audience: Audience, type: MessageType, message: String, vararg replacements: Any, playSound: Boolean = true) {
        audience.sendMessage(MiniMessage.miniMessage().deserialize(type.prefix + String.format(message, *replacements)))
        if (playSound) {
            sound(audience, type.soundType?.sound)
        }
    }

    fun sound(audience: Audience, sound: Sound?) {
        if (sound == null) return
        audience.playSound(sound)
    }

    fun sound(audience: Audience, sound: SoundType?) {
        if (sound == null) return
        audience.playSound(sound.sound)
    }

}