package world.cepi.kstom.nbt

import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag

fun TagBlock(name: String) = Tag.String(name).map<Block>(
    get@{ map ->
        return@get Block.fromNamespaceId(map)
    },
    set@{ value ->
        value.namespace().toString()
    }
)