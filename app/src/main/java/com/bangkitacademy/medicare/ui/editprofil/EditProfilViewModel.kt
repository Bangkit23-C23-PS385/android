package com.bangkitacademy.medicare.ui.editprofil

import androidx.lifecycle.ViewModel
import com.bangkitacademy.medicare.data.remote.request.UpdateProfileRequest
import com.bangkitacademy.medicare.repository.AppRepository

class EditProfilViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun updateProfile(body: UpdateProfileRequest) = appRepository.updateProfile(body)
}