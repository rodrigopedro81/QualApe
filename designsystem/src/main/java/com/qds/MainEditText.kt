package com.qds

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.qds.databinding.CustomMainEditTextBinding

class MainEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val text get() = binding.field.text.toString()
    val editText get() = binding.field
    private var errorColor: Int = 0
    private var corner: Float = 0F
    private var strokeWidth: Float = 0F
    private var mainColor: Int = 0
    private var fieldIsValid = true
        set(value) {
            field = value
            setupViewByState()
        }

    private val binding = CustomMainEditTextBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?) {
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
            errorColor = attributes.getColor(R.styleable.MainEditText_errorColor, 0)
            corner = attributes.getDimension(R.styleable.MainEditText_corner, 0F)
            strokeWidth = attributes.getDimension(R.styleable.MainEditText_strokeWidth, 0F)
            mainColor = attributes.getColor(R.styleable.MainEditText_mainColor, 0)
            changeBackground(mainColor)
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

    private fun setupViewByState() {
        if (fieldIsValid.not()) {
            binding.field.setTextColor(errorColor)
            if (binding.field.compoundDrawables[0] != null) {
                binding.field.compoundDrawables[0].setTint(errorColor)
            }
        }
    }

    private fun changeBackground(newColor: Int) {
        val newBackground = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = corner
            color = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
            setStroke(strokeWidth.toInt(), newColor)
        }
        if (binding.field.compoundDrawables[0] != null) {
            binding.field.compoundDrawables[0].setTint(newColor)
        }
        binding.field.setHintTextColor(newColor)
        binding.textViewTitle.setTextColor(newColor)
        binding.field.background = newBackground
    }
}