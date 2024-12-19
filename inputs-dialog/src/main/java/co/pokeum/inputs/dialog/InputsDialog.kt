package co.pokeum.inputs.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ScrollView
import co.pokeum.inputs.dialog.model.*
import co.pokeum.inputs.dialog.ui.Inputs
import co.pokeum.inputs.dialog.util.dpToPixels

class InputsDialog @JvmOverloads constructor(
    context: Context?,
    title: String,
    inputModels: List<InputModel>,
    private val listener: InputsDialogInterface.OnClickListener,
    private val listenerId: Int = DEFAULT_LISTENER_ID,
    private val positiveButton: ButtonModel = DEFAULT_POSITIVE_BUTTON,
    private val negativeButton: ButtonModel = DEFAULT_NEGATIVE_BUTTON
) : AlertDialog(context) {

    private lateinit var inputs: Inputs

    init {
        if (title.isNotBlank()) {
            setTitle(title)
        }
        setInputs(context, inputModels)
        setButtons()
    }

    private fun setInputs(context: Context?, inputModels: List<InputModel>) {
        context?.let {
            val scrollView = ScrollView(it)
            // Set padding for the ScrollView
            val paddingInPixels = it.dpToPixels(SCROLL_VIEW_PADDING_DP)
            scrollView.setPadding(
                paddingInPixels,
                paddingInPixels,
                paddingInPixels,
                paddingInPixels
            )

            inputs = Inputs(it, inputModels)
            scrollView.addView(inputs)

            setView(scrollView)
        }
    }

    private fun setButtons() {
        setButton(BUTTON_POSITIVE, positiveButton.text) { _, _ -> }
        setButton(BUTTON_NEGATIVE, negativeButton.text) { _, _ -> }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        getButton(BUTTON_POSITIVE).contentDescription = positiveButton.contentDescription
        getButton(BUTTON_NEGATIVE).contentDescription = negativeButton.contentDescription
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setOnShowListener {
            getButton(BUTTON_POSITIVE).setOnClickListener {
                listener.onPositiveButtonClick(this, listenerId, inputs.getResult())
            }
            getButton(BUTTON_NEGATIVE).setOnClickListener {
                listener.onNegativeButtonClick(this, listenerId)
            }
        }
    }

    companion object {
        private const val SCROLL_VIEW_PADDING_DP = 16

        /* Default */
        internal const val DEFAULT_LISTENER_ID: Int = Int.MIN_VALUE
        internal val DEFAULT_POSITIVE_BUTTON: ButtonModel = ButtonModel("ok", "OK")
        internal val DEFAULT_NEGATIVE_BUTTON: ButtonModel = ButtonModel("cancel", "Cancel")
    }

    class Builder(private val context: Context) {
        private var title: String = ""
        private var inputModels: List<InputModel> = listOf()
        private var listener: InputsDialogInterface.OnClickListener? = null
        private var listenerId: Int = DEFAULT_LISTENER_ID
        private var positiveButtonContentDescription: String =
            DEFAULT_POSITIVE_BUTTON.contentDescription
        private var positiveButtonText: String = DEFAULT_POSITIVE_BUTTON.text
        private var negativeButtonContentDescription: String =
            DEFAULT_NEGATIVE_BUTTON.contentDescription
        private var negativeButtonText: String = DEFAULT_NEGATIVE_BUTTON.text

        fun setTitle(text: String): Builder {
            title = text
            return this
        }

        fun setInputs(inputs: List<InputModel>): Builder {
            inputModels = inputs
            return this
        }

        fun setListener(listener: InputsDialogInterface.OnClickListener): Builder {
            this.listener = listener
            return this
        }

        fun setListenerId(id: Int): Builder {
            listenerId = id
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

        fun build() = InputsDialog(
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
            positiveButton = ButtonModel(
                contentDescription = positiveButtonContentDescription,
                text = positiveButtonText
            ),
            negativeButton = ButtonModel(
                contentDescription = negativeButtonContentDescription,
                text = negativeButtonText
            )
        )
    }
}