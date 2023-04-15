package io.github.lee0701.mboard.module.softkeyboard

import kotlinx.serialization.Serializable

@Serializable
data class Keyboard(
    val rows: List<Row> = listOf(),
    val height: Float = 200f,
) {
    operator fun plus(another: Keyboard): Keyboard {
        return Keyboard(
            rows = this.rows + another.rows,
            height = this.height + another.height,
        )
    }
}