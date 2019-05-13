package com.krzdabrowski.airpurrr.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isFormValid = MutableLiveData<Boolean>()
    val isEmailError = ObservableBoolean()
    val isPasswordError = ObservableBoolean()

    fun isEmailValid(login: String) = isEmailError.set(login.isBlank())
    fun isPasswordValid(password: String) = isPasswordError.set(password.isBlank())

    fun onLoginButtonClick() {
        if (email.value == null || password.value == null || isEmailError.get() || isPasswordError.get()) {
            return
        }
        isFormValid.value = true
    }
}