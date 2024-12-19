package co.pokeum.inputs.dialog.model

enum class InputModelType { KeyValue, KeyValueType }

class InputModelBuilder(
    private val key: String,
    private val modelType: InputModelType
) {
    private var keyText: String = ""
    private var keyIsVisible: Boolean = true
    private var valueContentDescription: String = ""
    private var valueHint: String = ""
    private var valueText = ""
    private var typeContentDescription: String = ""
    private var typeList: List<String> = listOf()
    private var typePosition = 0

    fun setLabel(text: String): InputModelBuilder {
        keyText = text
        return this
    }

    fun hideLabel(): InputModelBuilder {
        keyIsVisible = false
        return this
    }

    fun setValueContentDescription(contentDescription: String): InputModelBuilder {
        valueContentDescription = contentDescription
        return this
    }

    fun setHint(text: String): InputModelBuilder {
        valueHint = text
        return this
    }

    fun setDefaultValue(text: String): InputModelBuilder {
        valueText = text
        return this
    }

    fun setTypeContentDescription(contentDescription: String): InputModelBuilder {
        typeContentDescription = contentDescription
        return this
    }

    fun setTypeList(types: List<String>): InputModelBuilder {
        typeList = types
        return this
    }

    fun setTypePosition(position: Int): InputModelBuilder {
        typePosition = position
        return this
    }

    fun build() = when (modelType) {
        InputModelType.KeyValue -> KeyValueModel(
            key = KeyModel(
                jsonKey = key,
                text = keyText,
                isVisible = keyIsVisible
            ),
            value = ValueModel(
                contentDescription = valueContentDescription,
                hint = valueHint,
                text = valueText
            )
        )

        InputModelType.KeyValueType -> KeyValueTypeModel(
            key = KeyModel(
                jsonKey = key,
                text = keyText,
                isVisible = keyIsVisible
            ),
            value = ValueModel(
                contentDescription = valueContentDescription,
                hint = valueHint,
                text = valueText
            ),
            type = TypeModel(
                contentDescription = typeContentDescription,
                types = typeList,
                position = typePosition
            )
        )
    }
}