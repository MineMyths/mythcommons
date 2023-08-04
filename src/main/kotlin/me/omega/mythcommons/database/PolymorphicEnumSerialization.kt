//package me.omega.mythcommons.database
//
//import kotlinx.serialization.SerializationException
//import kotlinx.serialization.encoding.Decoder
//import kotlinx.serialization.encoding.Encoder
//import me.omega.mythstom.lib.PolymorphicEnum
//import me.omega.mythstom.lib.capitalizeWords
//import me.omega.mythstom.server.Legend
//import kotlin.reflect.KClass
//
//fun serializePolymorphicEnum(encoder: Encoder, value: PolymorphicEnum) {
//    encoder.encodeString("${value::class.java.name}-${value.display}")
//}
//
//fun deserializePolymorphicEnum(decoder: Decoder, clazz: KClass<*>): PolymorphicEnum {
//    Legend.logger.info("Deserializing polymorphic enum")
//    val inp = decoder.decodeString()
//    val split = inp.split("-")
//    val enum = Class.forName(split[0]).enumConstants?.firstOrNull {
//
//        if (clazz.isInstance(it)) {
//            return@firstOrNull (it as PolymorphicEnum).display == split[1].capitalizeWords()
//        }
//
//        return@firstOrNull false
//
//    } ?: throw SerializationException("Enum constant ${split[1]} not found in ${split[0]}")
//    return enum as PolymorphicEnum
//}