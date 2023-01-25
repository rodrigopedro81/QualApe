package com.authentication

interface Authenticator {

    fun userIsAuthenticated() : Boolean

    fun userEmail() : String?

    fun login(
        email:String,
        password:String,
        callback: (isSuccessful: Boolean, errorMessage: String?) -> Unit
    )

    fun register(
        email: String,
        password: String,
        callback: (isSuccessful: Boolean, errorMessage: String?) -> Unit
    )
}