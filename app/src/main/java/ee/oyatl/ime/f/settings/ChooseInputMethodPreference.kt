package ee.oyatl.ime.f.settings

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import androidx.preference.Preference
import ee.oyatl.ime.f.R

class ChooseInputMethodPreference(
    context: Context,
    attrs: AttributeSet?,
): Preference(context, attrs) {
    init {
        layoutResource = R.layout.preference_inline
    }
    override fun onClick() {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showInputMethodPicker()
    }
}