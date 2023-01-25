package com.viewModel

import androidx.lifecycle.ViewModel
import com.authentication.Authenticator
import com.firestore.FirestoreRepository
import com.domain.model.UserInfo

class RegisterViewModel(
    private val authenticator: Authenticator,
    private val firestoreRepository: FirestoreRepository
): ViewModel() {

    lateinit var userInfo: UserInfo

    fun saveUserInfo(userInfo: UserInfo) {
        this.userInfo = userInfo
    }

    fun register(
        password: String,
        onComplete: (isSuccessful: Boolean, errorMessage: String?) -> Unit
    ) {
        authenticator.userEmail()?.let { email ->
            authenticator.register(
                email = email,
                password = password,
                callback = onComplete
            )
        }
    }

    fun saveUserData(onComplete: (wasSuccessful: Boolean) -> Unit) {
        firestoreRepository.saveUserData(userInfo, onComplete)
    }
}
