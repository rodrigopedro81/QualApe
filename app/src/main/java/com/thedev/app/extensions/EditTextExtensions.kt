package com.thedev.app.extensions

import android.util.Patterns
import android.widget.EditText

fun EditText.isNotEmpty(): Boolean = this.text.isNullOrEmpty().not()

fun EditText.isWithinLimits(min: Int, max: Int) : Boolean = this.text.length in min..max

fun EditText.checkValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this.text).matches() &&
            this.isNotEmpty()
}

fun EditText.checkValidPassword(): Boolean {
    return when {
        this.isWithinLimits(6, 32)
                && this.hasSpecialChar()
                && this.hasUpperAndLowerCases()
                && this.hasNumber() -> true
        else -> false
    }
}

fun EditText.hasSpecialChar() = (this.text.toString() != this.text.toString().filter {
    it.isLetterOrDigit()
})

fun EditText.hasUpperAndLowerCases(): Boolean {
    var upper = 0
    var lower = 0
    for (char in this.text) {
        if (char.isUpperCase()) upper += 1
        if (char.isLowerCase()) lower += 1
    }
    return upper != 0 && lower != 0
}

fun EditText.hasNumber(): Boolean {
    var number = 0
    for (char in this.text) {
        if (char.isDigit()) number += 1
    }
    return number > 0
}
