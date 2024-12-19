package co.pokeum.inputs.dialog.ui.internal

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import co.pokeum.inputs.dialog.R
import co.pokeum.inputs.dialog.model.*

internal class KeyValueTypeInput @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Input(context, attrs, defStyleAttr) {

    private val typeSpinner: Spinner = findViewById(R.id.typeSpinner)

    override fun inflate() {
        (context as Activity).layoutInflater.inflate(R.layout.key_value_type, this, true)
    }

    override fun jsonValue() = ResultModel(getValue(), typeSpinner.selectedItemPosition).json()

    fun setType(typeModel: TypeModel) {
        typeSpinner.contentDescription = typeModel.contentDescription

        val types = typeModel.types
        val position = typeModel.position

        typeSpinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, types)
        if (position >= 0 && position < types.size) {
            typeSpinner.setSelection(position)
        }

        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                typeModel.position = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}