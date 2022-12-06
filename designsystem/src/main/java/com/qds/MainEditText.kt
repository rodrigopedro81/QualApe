package com.qds

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.qds.databinding.CustomMainEditTextBinding

class MainEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){

    private var text: String? = null

    private val binding = CustomMainEditTextBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?){
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.MainEditText
            )
            val icon = attributes.getDrawable(R.styleable.MainEditText_startIcon)
            setIcon(icon)
            val label = attributes.getString(R.styleable.MainEditText_label)
            setLabel(label)
            val hint = attributes.getString(R.styleable.MainEditText_hint)
            setHint(hint)

            attributes.recycle()

        }
    }

    private fun setHint(hint: String?) {
        binding.field.hint = hint
    }

    private fun setLabel(label: String?) {
        binding.textViewTitle.text = label
    }

    private fun setIcon(icon: Drawable?) {
        binding.field.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
    }

}