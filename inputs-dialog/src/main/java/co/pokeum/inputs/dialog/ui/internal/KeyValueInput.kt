package co.pokeum.inputs.dialog.ui.internal

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import co.pokeum.inputs.dialog.R
import co.pokeum.inputs.dialog.model.*

internal class KeyValueInput @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Input(context, attrs, defStyleAttr) {

    override fun inflate() {
        (context as Activity).layoutInflater.inflate(R.layout.key_value, this, true)
    }

    override fun jsonValue() = ResultModel(getValue()).json()
}