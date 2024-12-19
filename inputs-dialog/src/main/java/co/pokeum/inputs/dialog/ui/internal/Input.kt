package co.pokeum.inputs.dialog.ui.internal

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import co.pokeum.inputs.dialog.R
import co.pokeum.inputs.dialog.model.*
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

internal abstract class Input(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    RelativeLayout(context, attrs, defStyleAttr) {

    private var keyText: TextInputLayout
    private var valueEditText: EditText

    init {
        this.inflate()

        keyText = findViewById(R.id.keyText)
        valueEditText = findViewById(R.id.valueEditText)
    }

    protected abstract fun inflate()

    fun jsonKey(): String = keyText.tag.toString()

    abstract fun jsonValue(): JSONObject

    fun setKey(keyModel: KeyModel) {
        keyText.apply {
            tag = keyModel.jsonKey
            if (keyModel.isVisible) {
                hint = keyModel.text.trim()
            }
        }
    }

    fun setValue(valueModel: ValueModel) {
        valueEditText.apply {
            contentDescription = valueModel.contentDescription

            if (keyText.hint.isNullOrEmpty()) { hint = valueModel.hint }
            setOnFocusChangeListener { view, hasFocus ->
                hint = if (hasFocus || keyText.hint.isNullOrEmpty()) { valueModel.hint } else { "" }

                // Show keyboard
                if (hasFocus) {
                    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
                }
            }
            setText(valueModel.text)
        }

        valueEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                valueModel.text = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    fun getValue() = valueEditText.text.toString()
}
