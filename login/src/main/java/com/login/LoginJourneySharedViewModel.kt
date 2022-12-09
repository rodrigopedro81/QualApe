package com.login

import androidx.lifecycle.ViewModel
import com.domain.model.User

class LoginJourneySharedViewModel: ViewModel() {

    private lateinit var user: User

    fun saveUserInfo(user: User) {
        this.user = user
    }
}
