package com.bangkitacademy.medicare.ui.profil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.bangkitacademy.medicare.data.preferences.UserPreference
import com.bangkitacademy.medicare.data.remote.request.CreateProfileRequest
import com.bangkitacademy.medicare.data.remote.response.GetProfileResponse
import com.bangkitacademy.medicare.data.remote.response.GetUserResponse
import com.bangkitacademy.medicare.repository.AppRepository
import kotlinx.coroutines.launch
import com.bangkitacademy.medicare.utils.Result

class ProfilViewModel(
    private val appRepository: AppRepository, private val preferences: UserPreference? = null
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            preferences?.clearUser()
        }
    }

    fun getProfile() = appRepository.getProfile()

    fun createProfile(body: CreateProfileRequest) = appRepository.createProfile(body)

}