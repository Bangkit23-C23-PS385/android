package com.bangkitacademy.medicare.ui.prediction

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.databinding.FragmentPredictionBinding
import com.bangkitacademy.medicare.ui.resultprediction.ResultPredictionActivity

class PredictionFragment : Fragment() {

    private var _binding: FragmentPredictionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var visibleLayoutIndex = -1

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("visibleLayoutIndex", visibleLayoutIndex)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[PredictionViewModel::class.java]

        _binding = FragmentPredictionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dropDownActivity()
        addSymptom(savedInstanceState)

        binding.predictionButton.setOnClickListener {
            val intent = Intent(requireContext(), ResultPredictionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addSymptom(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            visibleLayoutIndex = savedInstanceState.getInt("visibleLayoutIndex", -1)
        }

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

    private fun dropDownActivity() {
        val items = resources.getStringArray(R.array.gejala)
        val adapter = ArrayAdapter(requireContext(), R.layout.question_adapter, items)
        binding.ddQuestion1.setAdapter(adapter)
        binding.ddQuestion2.setAdapter(adapter)
        binding.ddQuestion3.setAdapter(adapter)
        binding.ddQuestion4.setAdapter(adapter)
        binding.ddQuestion5.setAdapter(adapter)
        binding.ddQuestion6.setAdapter(adapter)
        binding.ddQuestion7.setAdapter(adapter)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
