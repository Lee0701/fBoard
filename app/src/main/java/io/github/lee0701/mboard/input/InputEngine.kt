package io.github.lee0701.mboard.input

import io.github.lee0701.mboard.service.KeyboardState

interface InputEngine {
    val listener: Listener

    fun onKey(code: Int, state: KeyboardState)
    fun onDelete()

    fun onReset()

    fun getLabels(state: KeyboardState): Map<Int, CharSequence>

    interface Listener {
        fun onComposingText(text: CharSequence)
        fun onFinishComposing()
        fun onCommitText(text: CharSequence)
        fun onDeleteText(beforeLength: Int, afterLength: Int)
        fun onCandidates(list: List<Candidate>)
        fun onSystemKey(code: Int): Boolean
        fun onEditorAction(code: Int)
    }
}