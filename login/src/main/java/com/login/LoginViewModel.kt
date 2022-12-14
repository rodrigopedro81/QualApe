package com.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.FieldsState
import com.domain.model.User

class LoginViewModel: ViewModel() {

    lateinit var user: User
    private val _fieldsState = MutableLiveData<FieldsState>(FieldsState.FieldsAreInvalid)
    val fieldsState : LiveData<FieldsState> get() = _fieldsState

    fun saveUserInfo(user: User) {
        this.user = user
    }

    fun setFieldsAsValid() {
        _fieldsState.value = FieldsState.FieldsAreValid
    }

    fun setFieldsAsInvalid() {
        _fieldsState.value = FieldsState.FieldsAreInvalid
    }
}
