package com.bangkitacademy.medicare.ui.resultprediction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bangkitacademy.medicare.MainActivity
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.data.remote.request.PredictionRequest
import com.bangkitacademy.medicare.databinding.ActivityResultPredictionBinding
import com.bangkitacademy.medicare.utils.Result
import com.bangkitacademy.medicare.utils.ViewModelFactory

class ResultPredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultPredictionBinding
    private val resultPredictionViewModel by viewModels<ResultPredictionViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }

        val selectedItems = intent.getStringArrayListExtra(EXTRA_RESULT)
        val body = PredictionRequest(selectedItems)
        resultPredictionViewModel.setPredictInfo(body)
        resultPredictionViewModel.predict().observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.imageView.visibility = View.VISIBLE
                    val disease = result.data.data.disease
                    val description = result.data.data.description
                    val precaution =
                        "Ada beberapa cara penanggulangannya seperti:\n" +
                        "● ${result.data.data.precaution1.replaceFirstChar { it.uppercase() }}\n" +
                        "● ${result.data.data.precaution2.replaceFirstChar { it.uppercase() }}\n" +
                        "● ${result.data.data.precaution3.replaceFirstChar { it.uppercase() }}\n" +
                        "● ${result.data.data.precaution4.replaceFirstChar { it.uppercase() }}\n"
                    binding.symptomName.text = disease
                    binding.symptomDescription.text = description
                    binding.preventionTitle.text = getString(R.string.precaution)
                    binding.preventionDescription.text = precaution
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        result.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

//    @Deprecated("Deprecated")
//    override fun onBackPressed() {
//        super.onBackPressed()
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()
//    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }
}