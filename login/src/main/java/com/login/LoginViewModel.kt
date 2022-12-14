package com.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.domain.model.User

class LoginViewModel: ViewModel() {

    lateinit var user: User
    private val _loginState = MutableLiveData<LoginState>(LoginState.FieldsAreInvalid)
    val loginState : LiveData<LoginState> get() = _loginState

    fun saveUserInfo(user: User) {
        this.user = user
    }

    fun setFieldsAsValid() {
        _loginState.value = LoginState.FieldsAreValid
    }

    fun setFieldsAsInvalid() {
        _loginState.value = LoginState.FieldsAreInvalid
    }
}

sealed class LoginState {
    object FieldsAreValid: LoginState()
    object FieldsAreInvalid: LoginState()
}
