package world.cepi.kstom.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

@Serializer(forClass = Component::class)
@OptIn(ExperimentalSerializationApi::class)
object ComponentSerializer : KSerializer<Component> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Component", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Component) {
        encoder.encodeString(GsonComponentSerializer.gson().serialize(value))
    }

    override fun deserialize(decoder: Decoder): Component {
        return GsonComponentSerializer.gson().deserialize(decoder.decodeString())
    }
}