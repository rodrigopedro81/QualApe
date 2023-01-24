package com.domain.commons

object Verifier {

    fun String.isWhatsappValid(): Boolean =
        android.util.Patterns.PHONE.matcher(this).matches()

    fun String.isApartmentValid(): Boolean {
        return if (this.toIntOrNull() != null) {
            (this.toInt() in 100..516)
        } else {
            false
        }
    }

    fun String.isBlockValid(): Boolean {
        return if (this.toIntOrNull() != null) {
            (this.toInt() in 1..9)
        } else {
            false
        }
    }

    fun String.isEmailValid(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun String.isNameValid(): Boolean =
        this.length > 6

    fun String.isPasswordValid(): Boolean =
        this.length > 6
}