package com.bangkitacademy.medicare.ui.prediction

import androidx.lifecycle.ViewModel
import com.bangkitacademy.medicare.repository.AppRepository

class PredictionViewModel(private val appRepository: AppRepository) : ViewModel() {

    fun getSymptoms() = appRepository.getSymptoms()
}