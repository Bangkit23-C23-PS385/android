package com.bangkitacademy.medicare.ui.prediction

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.data.remote.request.PredictionRequest
import com.bangkitacademy.medicare.databinding.FragmentPredictionBinding
import com.bangkitacademy.medicare.ui.resultprediction.ResultPredictionActivity
import com.bangkitacademy.medicare.ui.resultprediction.ResultPredictionActivity.Companion.EXTRA_RESULT
import com.bangkitacademy.medicare.utils.Result
import com.bangkitacademy.medicare.utils.ViewModelFactory
import kotlinx.coroutines.NonDisposableHandle.parent

class PredictionFragment : Fragment() {

    private var _binding: FragmentPredictionBinding? = null
    private val binding get() = _binding!!
    private val predictionViewModel by viewModels<PredictionViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    private var visibleLayoutIndex = -1
    private val selectedItems = mutableListOf<String>()
    private var selectedSymptomsCount = 0

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("visibleLayoutIndex", visibleLayoutIndex)
        outState.putInt("selectedSymptomsCount", selectedSymptomsCount)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPredictionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        visibleLayoutIndex = savedInstanceState?.getInt("visibleLayoutIndex", -1) ?: -1
        selectedSymptomsCount = savedInstanceState?.getInt("selectedSymptomsCount", 0) ?: 0

        setupAction()
        addSymptom()
        checkButton()
    }

    private fun checkButton() {
        when (selectedSymptomsCount) {
            0,1,2 -> binding.predictionButton.isEnabled = false
            else -> binding.predictionButton.isEnabled = true
        }
    }

    private fun addSymptom() {

        when (visibleLayoutIndex) {
            0 -> binding.ddLayoutQuestion4.visibility = View.VISIBLE
            1 -> {
                binding.ddLayoutQuestion4.visibility = View.VISIBLE
                binding.ddLayoutQuestion5.visibility = View.VISIBLE
            }
            2 -> {
                binding.ddLayoutQuestion4.visibility = View.VISIBLE
                binding.ddLayoutQuestion5.visibility = View.VISIBLE
                binding.ddLayoutQuestion6.visibility = View.VISIBLE
            }
            3 -> {
                binding.ddLayoutQuestion4.visibility = View.VISIBLE
                binding.ddLayoutQuestion5.visibility = View.VISIBLE
                binding.ddLayoutQuestion6.visibility = View.VISIBLE
                binding.ddLayoutQuestion7.visibility = View.VISIBLE
                binding.floatingActionButton.hide()
            }
        }

        binding.floatingActionButton.setOnClickListener {
            visibleLayoutIndex++
            when (visibleLayoutIndex) {
                0 -> {
                    binding.ddLayoutQuestion4.visibility = View.VISIBLE
                }

                1 -> {
                    binding.ddLayoutQuestion5.visibility = View.VISIBLE
                }

                2 -> {
                    binding.ddLayoutQuestion6.visibility = View.VISIBLE
                }

                3 -> {
                    binding.ddLayoutQuestion7.visibility = View.VISIBLE
                    binding.floatingActionButton.hide()
                }
            }
        }
    }

    private fun setupAction() {

        var selectedItem1 : String? = null
        var selectedItem2 : String? = null
        var selectedItem3 : String? = null
        var selectedItem4 : String? = null
        var selectedItem5 : String? = null
        var selectedItem6 : String? = null
        var selectedItem7 : String? = null

        binding.predictionButton.isEnabled = false

        predictionViewModel.getSymptoms().observe(requireActivity()) { result ->
            when (result) {
                is Result.Success -> {
                    val items = result.data.data.symptoms
                    val symptomsId = items.map { it.symptomId }
                    val adapter = ArrayAdapter(
                        requireContext(),
                        R.layout.question_adapter,
                        symptomsId
                    )

                    binding.ddQuestion1.setAdapter(adapter)
                    binding.ddQuestion1.setOnItemClickListener { _, _, position, _ ->
                        val selectedSymptoms = items[position]
                        selectedItem1 = selectedSymptoms.symptomEn
                        selectedSymptomsCount++
                        updateButtonState()
                    }
                    binding.ddQuestion2.setAdapter(adapter)
                    binding.ddQuestion2.setOnItemClickListener { _, _, position, _ ->
                        val selectedSymptoms = items[position]
                        selectedItem2 = selectedSymptoms.symptomEn
                        selectedSymptomsCount++
                        updateButtonState()
                    }
                    binding.ddQuestion3.setAdapter(adapter)
                    binding.ddQuestion3.setOnItemClickListener { _, _, position, _ ->
                        val selectedSymptoms = items[position]
                        selectedItem3 = selectedSymptoms.symptomEn
                        selectedSymptomsCount++
                        updateButtonState()
                    }
                    binding.ddQuestion4.setAdapter(adapter)
                    binding.ddQuestion4.setOnItemClickListener { _, _, position, _ ->
                        val selectedSymptoms = items[position]
                        selectedItem4 = selectedSymptoms.symptomEn
                        selectedSymptomsCount++
                        updateButtonState()
                    }
                    binding.ddQuestion5.setAdapter(adapter)
                    binding.ddQuestion5.setOnItemClickListener { _, _, position, _ ->
                        val selectedSymptoms = items[position]
                        selectedItem5 = selectedSymptoms.symptomEn
                        selectedSymptomsCount++
                        updateButtonState()
                    }
                    binding.ddQuestion6.setAdapter(adapter)
                    binding.ddQuestion6.setOnItemClickListener { _, _, position, _ ->
                        val selectedSymptoms = items[position]
                        selectedItem6 = selectedSymptoms.symptomEn
                        selectedSymptomsCount++
                        updateButtonState()
                    }
                    binding.ddQuestion7.setAdapter(adapter)
                    binding.ddQuestion7.setOnItemClickListener { _, _, position, _ ->
                        val selectedSymptoms = items[position]
                        selectedItem7 = selectedSymptoms.symptomEn
                        selectedSymptomsCount++
                        updateButtonState()
                    }
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.predictionButton.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        result.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Result.Loading -> {}
            }
        }

        binding.predictionButton.setOnClickListener {
            for (i in 1..7) {
                val selectedItem = when (i) {
                    1 -> selectedItem1
                    2 -> selectedItem2
                    3 -> selectedItem3
                    4 -> selectedItem4
                    5 -> selectedItem5
                    6 -> selectedItem6
                    7 -> selectedItem7
                    else -> throw IllegalArgumentException("Invalid index $i")
                }
                selectedItems.add(selectedItem.toString())
            }
            Log.d("selectedItems", selectedItems.toString())

            activity?.let {
                val intent = Intent (it, ResultPredictionActivity::class.java)
                intent.putStringArrayListExtra(EXTRA_RESULT, ArrayList(selectedItems))
                it.startActivity(intent)
                it.finish()
            }

            resetView()
        }
    }

    private fun resetView(){
        selectedItems.removeAll(selectedItems)
        for (i in 1..7) {
            val question = when (i) {
                1 -> binding.ddQuestion1
                2 -> binding.ddQuestion2
                3 -> binding.ddQuestion3
                4 -> binding.ddQuestion4
                5 -> binding.ddQuestion5
                6 -> binding.ddQuestion6
                7 -> binding.ddQuestion7
                else -> throw IllegalArgumentException("Invalid index $i")
            }
            question.setText("", false)
        }
        visibleLayoutIndex = -1
        selectedSymptomsCount = 0
        updateButtonState()
    }

    private fun updateButtonState() {
        binding.predictionButton.isEnabled = selectedSymptomsCount >= 3
        Log.d("selectedSymptomsCount", selectedSymptomsCount.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
