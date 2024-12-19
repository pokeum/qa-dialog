package co.pokeum.inputs.dialog.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import co.pokeum.inputs.dialog.model.*
import co.pokeum.inputs.dialog.ui.internal.*
import org.json.JSONObject

@SuppressLint("ViewConstructor")
internal class Inputs(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    inputModels: List<InputModel>
) : LinearLayout(context, attrs, defStyleAttr) {

    private val inputs: MutableList<Input> = mutableListOf()

    constructor(context: Context?, inputModels: List<InputModel>)
            : this(context, null, inputModels)

    constructor(context: Context?, attrs: AttributeSet?, inputModels: List<InputModel>)
            : this(context, attrs, 0, inputModels)

    init {
        orientation = VERTICAL
        setInputModels(inputModels)
    }

    private fun setInputModels(inputModels: List<InputModel>) {
        for (inputModel in inputModels) {
            val input: Input? = when (inputModel) {
                is KeyValueModel -> {
                    KeyValueInput(context).apply {
                        setKey(inputModel.key)
                        setValue(inputModel.value)
                    }
                }

                is KeyValueTypeModel -> {
                    KeyValueTypeInput(context).apply {
                        setKey(inputModel.key)
                        setValue(inputModel.value)
                        setType(inputModel.type)
                    }
                }

                else -> null
            }
            input?.let { inputs.add(it); addView(it) }
        }
    }

    fun getResult(): String = JSONObject().apply {
        for (input in inputs) {
            put(input.jsonKey(), input.jsonValue())
        }
    }.toString()
}
