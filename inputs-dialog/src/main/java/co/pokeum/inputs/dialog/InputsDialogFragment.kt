package co.pokeum.inputs.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import co.pokeum.inputs.dialog.InputsDialog.Companion.DEFAULT_LISTENER_ID
import co.pokeum.inputs.dialog.InputsDialog.Companion.DEFAULT_NEGATIVE_BUTTON
import co.pokeum.inputs.dialog.InputsDialog.Companion.DEFAULT_POSITIVE_BUTTON
import co.pokeum.inputs.dialog.model.*
import kotlin.properties.Delegates

class InputsDialogFragment : DialogFragment() {

    private lateinit var title: String
    private lateinit var inputModels: List<InputModel>
    private lateinit var positiveButton: ButtonModel
    private lateinit var negativeButton: ButtonModel
    private var listenerId by Delegates.notNull<Int>()
    private var listener: InputsDialogInterface.OnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            title = savedInstanceState.getString(KEY_TITLE)!!
            inputModels = savedInstanceState.getParcelableArrayList(KEY_INPUT_MODELS)!!
            positiveButton = savedInstanceState.getParcelable(KEY_POSITIVE_BUTTON)!!
            negativeButton = savedInstanceState.getParcelable(KEY_NEGATIVE_BUTTON)!!
            listenerId = savedInstanceState.getInt(KEY_LISTENER_ID)
            try {
                listener = activity as InputsDialogInterface.OnClickListener
            } catch (_: Throwable) {
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TITLE, title)
        outState.putParcelableArrayList(KEY_INPUT_MODELS, ArrayList(inputModels))
        outState.putParcelable(KEY_POSITIVE_BUTTON, positiveButton)
        outState.putParcelable(KEY_NEGATIVE_BUTTON, negativeButton)
        outState.putInt(KEY_LISTENER_ID, listenerId)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return InputsDialog(
            context = context,
            title = title,
            inputModels = inputModels,
            listener = object : InputsDialogInterface.OnClickListener {

                override fun onPositiveButtonClick(
                    dialog: DialogInterface?,
                    id: Int,
                    result: String
                ) {
                    listener?.onPositiveButtonClick(dialog, id, result)
                }

                override fun onNegativeButtonClick(dialog: DialogInterface?, id: Int) {
                    listener?.onNegativeButtonClick(dialog, id)
                }
            },
            listenerId = listenerId,
            positiveButton = positiveButton,
            negativeButton = negativeButton
        )
    }

    fun show(manager: FragmentManager) =
        show(manager, InputsDialogFragment::class.java.simpleName)

    class Builder {
        private var _title: String = ""
        private var _inputModels: List<InputModel> = listOf()
        private var _listener: InputsDialogInterface.OnClickListener? = null
        private var _listenerId: Int = DEFAULT_LISTENER_ID
        private var positiveButtonContentDescription: String =
            DEFAULT_POSITIVE_BUTTON.contentDescription
        private var positiveButtonText: String = DEFAULT_POSITIVE_BUTTON.text
        private var negativeButtonContentDescription: String =
            DEFAULT_NEGATIVE_BUTTON.contentDescription
        private var negativeButtonText: String = DEFAULT_NEGATIVE_BUTTON.text

        fun setTitle(text: String): Builder {
            _title = text
            return this
        }

        fun setInputs(inputs: List<InputModel>): Builder {
            _inputModels = inputs
            return this
        }

        fun setListener(listener: InputsDialogInterface.OnClickListener): Builder {
            _listener = listener
            return this
        }

        fun setListenerId(id: Int): Builder {
            _listenerId = id
            return this
        }

        fun setPositiveButtonContentDescription(contentDescription: String): Builder {
            positiveButtonContentDescription = contentDescription
            return this
        }

        fun setPositiveButtonText(text: String): Builder {
            positiveButtonText = text
            return this
        }

        fun setNegativeButtonContentDescription(contentDescription: String): Builder {
            negativeButtonContentDescription = contentDescription
            return this
        }

        fun setNegativeButtonText(text: String): Builder {
            negativeButtonText = text
            return this
        }

        fun build() = InputsDialogFragment().apply {
            title = _title
            inputModels = _inputModels
            listener = _listener
            listenerId = _listenerId
            positiveButton = ButtonModel(
                contentDescription = positiveButtonContentDescription,
                text = positiveButtonText
            )
            negativeButton = ButtonModel(
                contentDescription = negativeButtonContentDescription,
                text = negativeButtonText
            )
        }
    }

    companion object {

        private const val KEY_TITLE = "title"
        private const val KEY_INPUT_MODELS = "input_models"
        private const val KEY_LISTENER_ID = "listener_id"
        private const val KEY_POSITIVE_BUTTON = "positive_button"
        private const val KEY_NEGATIVE_BUTTON = "negative_button"
    }
}