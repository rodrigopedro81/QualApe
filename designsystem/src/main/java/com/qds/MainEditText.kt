package com.qds

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.qds.databinding.CustomMainEditTextBinding

class MainEditText @JvmOverloads constructor(
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
    private var _fieldIsValid = false
        set(value) {
            field = value
            setupViewByState()
        }
    val fieldIsValid get() = _fieldIsValid

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
//            setAsPassword(attributes.getInteger(R.styleable.MainEditText_android_inputType, 0))
            changeBackground(mainColor)
            editText.animateFocus(150L)
            attributes.recycle()
        }
    }

//    private fun setAsPassword(type: Int) {
//        if (type == PASSWORD_FLAG) {
//            editText.transformationMethod = PasswordTransformationMethod.getInstance()
//
//        }
//    }

    fun setValidationScript(validationRule: (String) -> Boolean, doAfter: () -> Unit = {}) {
        editText.doAfterTextChanged {
            _fieldIsValid = validationRule.invoke(it.toString())
            doAfter.invoke()
        }
    }

    fun setValidationScript(validationRule: (String) -> Boolean) {
        editText.doAfterTextChanged {
            _fieldIsValid = if (it.isNullOrBlank()) {
                validationRule.invoke("")
            } else {
                validationRule.invoke(it.toString())
            }
        }
    }

    private fun setHint(hint: String?) {
        binding.customMainEditText.hint = hint
    }

    private fun setLabel(label: String?) {
        binding.textViewLabel.text = label
    }

    private fun setIcon(icon: Drawable?) {
        binding.textViewLabel.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
    }

    private fun setupViewByState() {
        changeBackground(
            newColor = if (_fieldIsValid) mainColor else errorColor
        )
    }

    private fun changeBackground(newColor: Int) {
        val newBackground = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = corner
            color = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
            setStroke(strokeWidth.toInt(), newColor)
        }
        if (binding.textViewLabel.compoundDrawables[0] != null) {
            binding.textViewLabel.compoundDrawables[0].setTint(newColor)
        }
        binding.textViewLabel.setHintTextColor(newColor)
        binding.textViewLabel.setTextColor(newColor)
        binding.textViewLabel.background = newBackground
    }

    companion object {
        private const val PASSWORD_FLAG = 0x00000081
    }
}