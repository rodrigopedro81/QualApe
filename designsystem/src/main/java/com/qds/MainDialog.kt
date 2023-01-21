package com.qds

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.qds.databinding.CustomMainDialogBinding

class MainDialog(
    context: Context,
    private val title: String = "",
    private val description: String,
    private val buttonText: String = "",
    private val buttonClickListener: (MainDialog) -> Unit,
    private val onDismiss: (MainDialog) -> Unit = {},
) : Dialog(context) {

    private val binding by lazy {
        CustomMainDialogBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupDialog()
    }

    private fun setupDialog() {
        with(binding) {
            if (title.isNotBlank()) textViewTitle.text = title else textViewTitle.visibility =
                View.GONE
            textViewDescription.text = description
            mainButton.apply {
                setOnClickListener { buttonClickListener.invoke(this@MainDialog) }
                text = buttonText
            }
        }
    }

    override fun dismiss() {
        onDismiss(this)
        super.dismiss()
    }

    companion object {

        fun Context.buildMainDialog(
            title: String = "",
            description: String,
            buttonClickListener: (MainDialog) -> Unit,
            buttonText: String = "",
            onDismiss: (MainDialog) -> Unit = {}
        ) = MainDialog(this, title, description, buttonText, buttonClickListener, onDismiss).show()
    }
}