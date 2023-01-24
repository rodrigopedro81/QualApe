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

class MainEditTextLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val text get() = binding.customMainEditText.text.toString()
    val editText get() = binding.customMainEditText
    private var errorColor: Int = 0
    private var corner: Float = 0F
    private var strokeWidth: Float = 0F
    private var mainColor: Int = 0
    var fieldIsValid = false

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
            changeColors(mainColor)
            editText.animateFocus(150L)
            attributes.recycle()
        }
    }

    private fun setHint(hint: String?) {
        binding.customMainEditText.hint = hint
    }

    private fun setLabel(label: String?) {
        binding.textViewLabel.text = label
    }

    private fun setIcon(icon: Drawable?) {
        binding.customMainEditText.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
    }

    fun setEditTextAsValid() {
        fieldIsValid = true
        changeColors(newColor = mainColor)
    }

    fun setEditTextAsInvalid() {
        fieldIsValid = false
        changeColors(newColor = errorColor)
    }


    private fun changeColors(newColor: Int) {
        val newBackground = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = corner
            color = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
            setStroke(strokeWidth.toInt(), newColor)
        }
        if (binding.customMainEditText.compoundDrawables[0] != null) {
            binding.customMainEditText.compoundDrawables[0].setTint(newColor)
        }
        binding.customMainEditText.setHintTextColor(newColor)
        binding.customMainEditText.setTextColor(newColor)
        binding.customMainEditText.background = newBackground
    }
}