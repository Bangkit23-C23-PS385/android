package com.bangkitacademy.medicare.ui.prediction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkitacademy.medicare.data.remote.request.PredictionRequest
import com.bangkitacademy.medicare.repository.AppRepository

class PredictionViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun getSymptoms() = appRepository.getSymptoms()
}