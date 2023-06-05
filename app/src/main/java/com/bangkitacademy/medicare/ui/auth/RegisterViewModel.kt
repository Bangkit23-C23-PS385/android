package com.bangkitacademy.medicare.ui.auth

import androidx.lifecycle.ViewModel
import com.bangkitacademy.medicare.data.remote.request.RegisterRequest
import com.bangkitacademy.medicare.repository.AuthenticationRepository

class RegisterViewModel(private val authenticationRepository: AuthenticationRepository): ViewModel() {
    private var registerInfo: RegisterRequest = RegisterRequest()

    fun register() = authenticationRepository.register(registerInfo)

    fun setRegisterInfo(body: RegisterRequest){
        registerInfo = body
    }
}