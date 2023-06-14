package com.bangkitacademy.medicare.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkitacademy.medicare.data.preferences.UserPreference
import com.bangkitacademy.medicare.di.Injection
import com.bangkitacademy.medicare.repository.AppRepository
import com.bangkitacademy.medicare.repository.AuthenticationRepository
import com.bangkitacademy.medicare.repository.NewsRepository
import com.bangkitacademy.medicare.ui.auth.AuthenticationViewModel
import com.bangkitacademy.medicare.ui.auth.LoginViewModel
import com.bangkitacademy.medicare.ui.auth.RegisterViewModel
import com.bangkitacademy.medicare.ui.beranda.BerandaViewModel
import com.bangkitacademy.medicare.ui.prediction.PredictionViewModel
import com.bangkitacademy.medicare.ui.profil.ProfilViewModel
import com.bangkitacademy.medicare.ui.resultprediction.ResultPredictionViewModel

class ViewModelFactory private constructor(
    private val newsRepository: NewsRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val pref: UserPreference,
    private val appRepository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BerandaViewModel::class.java)) {
            return BerandaViewModel(newsRepository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authenticationRepository) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(authenticationRepository) as T
        }
        if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
            return AuthenticationViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(ProfilViewModel::class.java)) {
            return ProfilViewModel(appRepository, pref) as T
        }
        if (modelClass.isAssignableFrom(PredictionViewModel::class.java)) {
            return PredictionViewModel(appRepository) as T
        }
        if (modelClass.isAssignableFrom(ResultPredictionViewModel::class.java)) {
            return ResultPredictionViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideNewsRepository(context),
                Injection.authenticationRepository(context),
                Injection.providePreferences(context),
                Injection.appRepository(context)
            )
        }.also {
            instance = it
        }
    }
}