package com.bangkitacademy.medicare.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkitacademy.medicare.data.preferences.UserModel
import com.bangkitacademy.medicare.data.preferences.UserPreference

class AuthenticationViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}