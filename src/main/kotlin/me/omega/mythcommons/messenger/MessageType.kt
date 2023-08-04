package me.omega.mythcommons.messenger

enum class MessageType(val prefix: String, val soundType: SoundType?) {

    ERROR("\ue800 <gray>", SoundType.ERROR),
    SERVER("\ue801 <gray>", SoundType.NOTIFY),
    TIP("\ue803 <gray>", SoundType.NOTIFY),

}