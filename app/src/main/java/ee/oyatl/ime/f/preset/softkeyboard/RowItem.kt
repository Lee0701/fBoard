package ee.oyatl.ime.f.preset.softkeyboard

import ee.oyatl.ime.f.preset.serialization.KeyCodeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable()
sealed interface RowItem {
    val width: Float
}

@SerialName("spacer")
@Serializable
data class Spacer(
    @Serializable override val width: Float = 1f,
): RowItem

@SerialName("key")
@Serializable
data class Key(
    @Serializable(with = KeyCodeSerializer::class) val code: Int = 0,
    val output: String? = null,
    val label: String? = output,
    val backgroundType: KeyBackgroundType? = null,
    val iconType: KeyIconType? = null,
    override val width: Float = 1f,
    val repeatable: Boolean = false,
    val type: KeyType = KeyType.Alphanumeric,
): RowItem

@SerialName("include")
@Serializable
data class Include(
    val name: String,
): RowItem {
    override val width: Float = 0f
}