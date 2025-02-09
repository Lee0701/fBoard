package ee.oyatl.ime.f.module.inputengine

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.appcompat.widget.LinearLayoutCompat
import ee.oyatl.ime.f.module.candidates.Candidate
import ee.oyatl.ime.f.module.component.InputViewComponent
import ee.oyatl.ime.f.preset.softkeyboard.Keyboard
import ee.oyatl.ime.f.service.KeyboardState

interface InputEngine {

    val listener: Listener
    var components: List<InputViewComponent>
    var symbolsInputEngine: InputEngine?
    var alternativeInputEngine: InputEngine?

    fun initView(context: Context): View? {
        val componentViews = components.map { it.initView(context) }
        return LinearLayoutCompat(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayoutCompat.VERTICAL
            componentViews.forEach { addView(it) }
        }
    }
    fun onReset()
    fun onResetComponents() {
        components.forEach { it.reset() }
    }

    fun onKey(code: Int, state: KeyboardState)
    fun onDelete()
    fun onTextAroundCursor(before: String, after: String)


    fun getLabels(state: KeyboardState): Map<Int, CharSequence>
    fun getIcons(state: KeyboardState): Map<Int, Drawable>
    fun getMoreKeys(state: KeyboardState): Map<Int, Keyboard>

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