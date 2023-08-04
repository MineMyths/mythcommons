package world.cepi.kstom.item

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import world.cepi.kstom.adventure.asMini

class KLore constructor(private val tagResolver: TagResolver = TagResolver.empty()) {

    val list = mutableListOf<Component>()

    operator fun Component.unaryPlus() {
        list.add(this)
    }

    operator fun String.unaryPlus() {
        list.add(this.asMini(tagResolver))
    }

}