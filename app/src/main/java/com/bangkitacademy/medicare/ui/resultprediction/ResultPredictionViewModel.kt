package com.bangkitacademy.medicare.ui.resultprediction

import androidx.lifecycle.ViewModel
import com.bangkitacademy.medicare.data.remote.request.PredictionRequest
import com.bangkitacademy.medicare.repository.AppRepository

class ResultPredictionViewModel(private val appRepository: AppRepository) : ViewModel() {

    private var predictInfo: PredictionRequest = PredictionRequest()

    fun predict() = appRepository.predict(predictInfo)

    fun setPredictInfo(body: PredictionRequest) {
        predictInfo = body
    }
}