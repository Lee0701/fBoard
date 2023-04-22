package io.github.lee0701.mboard.module.table

import io.github.lee0701.mboard.module.serialization.HexIntSerializer
import io.github.lee0701.mboard.module.serialization.KeyCodeSerializer
import io.github.lee0701.mboard.service.KeyboardState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("simple")
class SimpleCodeConvertTable(
    @Serializable val map: Map<
            @Serializable(with = KeyCodeSerializer::class) Int,
            Entry> = mapOf(),
): CodeConvertTable {

    val reversedMap: Map<Pair<Int, EntryKey>, Int> = map.flatMap { (key, value) ->
        value.explode().map { (entryKey, charCode) -> (charCode to entryKey) to key }
    }.toMap()

    override fun get(keyCode: Int, state: KeyboardState): Int? {
        return map[keyCode]?.withKeyboardState(state)
    }

    override fun getAllForState(state: KeyboardState): Map<Int, Int> {
        return map.map { (k, v) -> v.withKeyboardState(state)?.let { k to it } }
            .filterNotNull()
            .toMap()
    }

    override fun getReversed(charCode: Int, entryKey: EntryKey): Int? {
        return reversedMap[charCode to entryKey]
    }

    override fun plus(table: CodeConvertTable): CodeConvertTable {
        return when(table) {
            is SimpleCodeConvertTable -> this + table
            is LayeredCodeConvertTable -> this + table
        }
    }

    operator fun plus(table: SimpleCodeConvertTable): SimpleCodeConvertTable {
        return SimpleCodeConvertTable(map = this.map + table.map)
    }

    operator fun plus(table: LayeredCodeConvertTable): LayeredCodeConvertTable {
        return LayeredCodeConvertTable(table.layers.mapValues { (_, table) ->
            this + table
        })
    }

    @Serializable
    data class Entry(
        @Serializable(with = HexIntSerializer::class) val base: Int? = null,
        @Serializable(with = HexIntSerializer::class) val shift: Int? = base,
        @Serializable(with = HexIntSerializer::class) val capsLock: Int? = shift,
        @Serializable(with = HexIntSerializer::class) val alt: Int? = base,
        @Serializable(with = HexIntSerializer::class) val altShift: Int? = shift,
    ) {
        fun withKeyboardState(keyboardState: KeyboardState): Int? {
            val shiftPressed = keyboardState.shiftState.pressed || keyboardState.shiftState.pressing
            val altPressed = keyboardState.altState.pressed || keyboardState.altState.pressing
            return if(keyboardState.shiftState.locked) capsLock
            else if(shiftPressed && altPressed) altShift
            else if(shiftPressed) shift
            else if(altPressed) alt
            else base
        }
        fun forKey(key: EntryKey): Int? {
            return when(key) {
                EntryKey.Base -> base
                EntryKey.Shift -> shift ?: base
                EntryKey.CapsLock -> capsLock ?: shift ?: base
                EntryKey.Alt -> alt ?: base
                EntryKey.AltShift -> altShift ?: alt
            }
        }
        fun explode(): Map<EntryKey, Int> {
            return listOfNotNull(
                base?.let { EntryKey.Base to it },
                shift?.let { EntryKey.Shift to it },
                capsLock?.let { EntryKey.CapsLock to it },
                alt?.let { EntryKey.Alt to it },
                altShift?.let { EntryKey.AltShift to it },
            ).toMap()
        }
    }

    enum class EntryKey {
        Base, Shift, CapsLock, Alt, AltShift;
        companion object {
            fun fromKeyboardState(keyboardState: KeyboardState): EntryKey {
                return if(keyboardState.altState.pressed && keyboardState.shiftState.pressed) AltShift
                else if(keyboardState.altState.pressed) Alt
                else if(keyboardState.shiftState.locked) CapsLock
                else if(keyboardState.shiftState.pressed) Shift
                else Base
            }
        }
    }
}