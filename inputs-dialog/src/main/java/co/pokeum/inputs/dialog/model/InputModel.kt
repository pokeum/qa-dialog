package co.pokeum.inputs.dialog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class KeyModel @JvmOverloads constructor(
    val jsonKey: String,
    val text: String,
    val isVisible: Boolean = true
) : Parcelable

@Parcelize
data class ValueModel @JvmOverloads constructor(
    val contentDescription: String,
    val hint: String = "",
    internal var text: String = ""
) : Parcelable

@Parcelize
data class TypeModel @JvmOverloads constructor(
    val contentDescription: String,
    val types: List<String>,
    internal var position: Int = 0
) : Parcelable

@Parcelize
data class ButtonModel(
    val contentDescription: String,
    val text: String
) : Parcelable

// ==================================================================================================== //

@Parcelize
open class InputModel(
    open val key: KeyModel,
    open val value: ValueModel
) : Parcelable

@Parcelize
class KeyValueModel(
    override val key: KeyModel,
    override val value: ValueModel
) : InputModel(key, value)

@Parcelize
class KeyValueTypeModel(
    override val key: KeyModel,
    override val value: ValueModel,
    val type: TypeModel
) : InputModel(key, value)