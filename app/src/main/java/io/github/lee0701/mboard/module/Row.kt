package io.github.lee0701.mboard.module

import kotlinx.serialization.Serializable

@Serializable
data class Row(
    val keys: List<Key> = listOf(),
    val padding: Float = 0f,
)