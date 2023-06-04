package com.bangkitacademy.medicare.ui.editprofil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.databinding.ActivityEditProfilBinding
import com.bangkitacademy.medicare.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker

class EditProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // handle top app bar back button
        binding.toolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        openDatePicker()

    }

    private fun openDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal Lahir")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        binding.edtTanggalLahir.setOnClickListener {
            datePicker.show(supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selectedDate ->
                binding.edtTanggalLahir.setText(datePicker.headerText)
            }
        }
    }
}