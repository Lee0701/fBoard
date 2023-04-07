package io.github.lee0701.mboard.module

import android.content.Context
import io.github.lee0701.mboard.keyboard.Key

data class Key(
    val code: Int,
    val output: String?,
    val label: String? = output,
    val iconType: KeyIconType? = null,
    val width: Float = 1f,
    val repeatable: Boolean = false,
    val type: KeyType = KeyType.Alphanumeric,
) {
    fun inflate(): Key {
        return Key(
            this.code,
            this.output,
            this.label,
            this.iconType,
            this.width,
            this.repeatable,
            this.type,
        )
    }
}