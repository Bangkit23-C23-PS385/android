package com.bangkitacademy.medicare.ui.auth

import androidx.lifecycle.ViewModel
import com.bangkitacademy.medicare.data.remote.request.LoginRequest
import com.bangkitacademy.medicare.repository.AuthenticationRepository

class LoginViewModel(private val authenticationRepository: AuthenticationRepository) : ViewModel() {
    private var loginInfo: LoginRequest = LoginRequest()

    fun login() = authenticationRepository.login(loginInfo)

    fun setLoginInfo(body: LoginRequest) {
        loginInfo = body
    }
}