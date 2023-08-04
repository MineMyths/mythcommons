package me.omega.mythcommons.messenger

import net.kyori.adventure.sound.Sound
import net.minestom.server.sound.SoundEvent

enum class SoundType(val sound: Sound) {

    FEEDBACK(Sound.sound().type(SoundEvent.BLOCK_NOTE_BLOCK_PLING).volume(1f).pitch(1f).build()),
    NOTIFY(Sound.sound().type(SoundEvent.ENTITY_EXPERIENCE_ORB_PICKUP).volume(1f).pitch(1f).build()),
    ERROR(Sound.sound().type(SoundEvent.BLOCK_NOTE_BLOCK_BASS).volume(1f).pitch(1f).build()),
    TELEPORT(Sound.sound().type(SoundEvent.ENTITY_ENDERMAN_TELEPORT).volume(1f).pitch(1f).build()),

}