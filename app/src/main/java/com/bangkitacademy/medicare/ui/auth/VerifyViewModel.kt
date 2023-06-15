package com.bangkitacademy.medicare.ui.auth

import androidx.lifecycle.ViewModel
import com.bangkitacademy.medicare.data.remote.request.ResendRequest
import com.bangkitacademy.medicare.repository.AuthenticationRepository

class VerifyViewModel(private val authenticationRepository: AuthenticationRepository): ViewModel() {
    private var resendInfo: ResendRequest = ResendRequest()

    fun resend() = authenticationRepository.resend(resendInfo)

    fun setResendInfo(body: ResendRequest){
        resendInfo = body
    }

}