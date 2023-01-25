package com.viewModel

import androidx.lifecycle.ViewModel
import com.authentication.Authenticator
import com.database.FirestoreRepository

class LoginViewModule(
    private val authenticator: Authenticator,
    private val firestoreRepository: FirestoreRepository
): ViewModel() {

    fun login(
        email: String,
        password: String,
        callback: (isSuccessful: Boolean, errorMessage: String?) -> Unit
    ) {
        authenticator.login(
            email = email,
            password = password,
            callback = callback
        )
    }

    fun checkIfUserIsLoggedIn(
        onResult: (isLoggedIn: Boolean, userEmail: String?) -> Unit
    ) {
        onResult.invoke(authenticator.userIsAuthenticated(), getUserEmail())
    }

    fun fetchUserDataForSessionModule(onComplete: (wasSuccessful: Boolean) -> Unit) {
        getUserEmail()?.let {
            firestoreRepository.fetchUserDataForSessionModule(it, onComplete)
        }
    }

    private fun getUserEmail() = authenticator.userEmail()
}