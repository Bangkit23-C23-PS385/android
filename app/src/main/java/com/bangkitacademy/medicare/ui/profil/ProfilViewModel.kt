package com.bangkitacademy.medicare.ui.profil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.bangkitacademy.medicare.data.preferences.UserPreference
import com.bangkitacademy.medicare.data.remote.response.GetUserResponse
import com.bangkitacademy.medicare.repository.AppRepository
import kotlinx.coroutines.launch
import com.bangkitacademy.medicare.utils.Result

class ProfilViewModel(
    private val appRepository: AppRepository,
    private val preferences: UserPreference
) :
    ViewModel() {

    private val _user = MutableLiveData<Result<GetUserResponse>>()
    val user: LiveData<Result<GetUserResponse>> = _user



    fun getUser() {
        viewModelScope.launch {
            val response = appRepository.getUser("13")
            response.asFlow().collect {
                _user.value = it
            }
        }
    }

}