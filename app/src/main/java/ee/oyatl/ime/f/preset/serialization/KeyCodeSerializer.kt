package ee.oyatl.ime.f.preset.serialization

import android.view.KeyEvent
import ee.oyatl.ime.f.preset.table.CustomKeycode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object KeyCodeSerializer: KSerializer<Int> {
    override val descriptor = PrimitiveSerialDescriptor("KeyCode", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Int) {
        val string = keyCodeToString(value)
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Int {
        val string = decoder.decodeString()
        return keyCodeFromString(string)
    }

    fun keyCodeToString(code: Int): String {
        val custom = CustomKeycode.values().find { it.code == code }?.name
        return custom ?: KeyEvent.keyCodeToString(code)
    }

    fun keyCodeFromString(string: String): Int {
        return try {
            CustomKeycode.valueOf(string).code
        } catch(ex: IllegalArgumentException) {
            val keyCode = KeyEvent.keyCodeFromString(string)
            if(keyCode > 0) keyCode else string.toIntOrNull() ?: 0
        }
    }
}