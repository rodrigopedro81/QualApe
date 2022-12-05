package com.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.qds.R
import com.qds.databinding.CustomMainEditTextBinding

class MainEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr){

    private var title: String? = null

    private val binding = CustomMainEditTextBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?){
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.CustomEditText
            )

            setBackgroundResource(R.drawable.shape_edittext)

            val titleResId = attributes.getResourceId(R.styleable.CustomEditText_custom_edittext, 0)
            if(titleResId != 0){
                title = context.getString(titleResId)
            }

            attributes.recycle()

        }
    }

}