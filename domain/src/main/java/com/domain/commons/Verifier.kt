package com.domain.commons

object Verifier {

    fun verifyWpp(wpp: String): Boolean =
        android.util.Patterns.PHONE.matcher(wpp).matches()

    fun verifyApartment(apartment: String): Boolean {
        return if (apartment.toIntOrNull() != null) {
            (apartment.toInt() in 100..516)
        } else {
            false
        }
    }

    fun verifyBlock(block: String): Boolean {
        return if (block.toIntOrNull() != null) {
            (block.toInt() in 1..9)
        } else {
            false
        }
    }

    fun verifyEmail(email: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun verifyName(name: String): Boolean =
        name.length > 6

    fun verifyPassword(password: String): Boolean =
        password.length > 6
}