package com.bangkitacademy.medicare.ui.resultprediction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkitacademy.medicare.databinding.ActivityResultPredictionBinding

class ResultPredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultPredictionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}