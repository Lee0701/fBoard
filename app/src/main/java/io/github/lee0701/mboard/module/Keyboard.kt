package io.github.lee0701.mboard.module

import io.github.lee0701.mboard.view.keyboard.Keyboard
import kotlinx.serialization.Serializable

@Serializable
data class Keyboard(
    val rows: List<Row> = listOf(),
    val height: Float = 200f,
) {
    fun inflate(): Keyboard {
        return io.github.lee0701.mboard.view.keyboard.Keyboard(
            rows = this.rows.map { it.inflate() },
            height = this.height,
        )
    }
}