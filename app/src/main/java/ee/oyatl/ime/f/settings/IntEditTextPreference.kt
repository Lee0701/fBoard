package ee.oyatl.ime.f.settings

import android.content.Context
import android.util.AttributeSet
import androidx.preference.EditTextPreference
import ee.oyatl.ime.f.R

class IntEditTextPreference(
    context: Context,
    attrs: AttributeSet?,
): EditTextPreference(context, attrs) {
    init {
        layoutResource = R.layout.preference_inline
    }

    override fun getPersistedString(defaultReturnValue: String?): String {
        return getPersistedInt(-1).let { if(it == -1) defaultReturnValue else it }.toString()
    }

    override fun persistString(value: String?): Boolean {
        return try {
            persistInt(Integer.valueOf(value ?: "-1"))
        } catch(e: NumberFormatException) {
            false
        }
    }
}