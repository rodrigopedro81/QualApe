package com.qds

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class MainButton(
    context: Context,
    attrs: AttributeSet?
) : AppCompatButton(context, attrs) {

    private var enabledColor: Int = 0
    private var enabledTextColor: Int = 0
    private var disabledColor: Int = 0
    private var disabledTextColor: Int = 0
    private var corner: Float = 0F
    private var strokeWidth: Float = 0F
    private var strokeColor: Int = 0
    private var buttonText: String = ""
    private var isButtonEnabled = true
        set(value) {
            field = value
            setButtonByState()
        }

    fun enableButton() { isButtonEnabled = true }
    fun disableButton() { isButtonEnabled = false }
    fun changeState() { isButtonEnabled = !isButtonEnabled }

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.MainButton)
            enabledColor = attributes.getColor(R.styleable.MainButton_enabledColor, 0)
            enabledTextColor = attributes.getColor(R.styleable.MainButton_enabledTextColor, 0)
            disabledColor = attributes.getColor(R.styleable.MainButton_disabledColor, 0)
            disabledTextColor = attributes.getColor(R.styleable.MainButton_disabledTextColor, 0)
            buttonText = attributes.getString(R.styleable.MainButton_android_text) ?: ""
            corner = attributes.getDimension(R.styleable.MainButton_corner, 0F)
            strokeWidth = attributes.getDimension(R.styleable.MainButton_buttonStrokeWidth, 0F)
            strokeColor = attributes.getColor(R.styleable.MainButton_buttonStrokeColor, 0)
            isButtonEnabled = true
            attributes.recycle()
        }
    }

    private fun setButtonByState() {
        if (isButtonEnabled) {
            changeBackground(enabledColor)
            setTextColor(enabledTextColor)
        } else {
            changeBackground(disabledColor)
            setTextColor(disabledTextColor)
        }
        text = buttonText
    }

    private fun changeBackground(newColor:Int) {
        val newBackground = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = corner
            color = ColorStateList.valueOf(newColor)
            setStroke(strokeWidth.toInt(), strokeColor)
        }
        background = newBackground
    }
    
}