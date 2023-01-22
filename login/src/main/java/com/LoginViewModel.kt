package com

import androidx.lifecycle.ViewModel
import com.domain.model.User

class LoginViewModel: ViewModel() {

    lateinit var user: User

    fun saveUserInfo(user: User) {
        this.user = user
    }
}
