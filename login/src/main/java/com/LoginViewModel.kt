package com

import androidx.lifecycle.ViewModel
import com.domain.model.UserInfo

class LoginViewModel: ViewModel() {

    lateinit var userInfo: UserInfo

    fun saveUserInfo(userInfo: UserInfo) {
        this.userInfo = userInfo
    }
}
