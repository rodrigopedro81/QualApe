package com.authentication

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Authentication {

    private const val UNKNOWN_ERROR = "An Unknown error has occurred"
    private val auth = Firebase.auth

    fun userIsAuthenticated() = auth.currentUser != null

    fun userEmail() = auth.currentUser?.email

    fun login(
        email:String,
        password:String,
        callback: (isSuccessful: Boolean, errorMessage: String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            callback.invoke(it.isSuccessful, it.exception?.message ?: UNKNOWN_ERROR)
        }
    }

    fun register(
        email: String,
        password: String,
        callback: (isSuccessful: Boolean, errorMessage: String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            callback.invoke(it.isSuccessful, it.exception?.message ?: UNKNOWN_ERROR)
        }
    }
}
