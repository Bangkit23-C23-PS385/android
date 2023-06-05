package com.bangkitacademy.medicare.ui.editprofil

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.bangkitacademy.medicare.databinding.ActivityEditProfilBinding
import com.bangkitacademy.medicare.utils.dateFormatToUser
import com.bangkitacademy.medicare.utils.uriToFile
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

        // handle button edit foto
        binding.lytFotoProfil.setOnClickListener {
            startGallery()
        }

        // handle date picker
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
                binding.edtTanggalLahir.setText(datePicker.headerText.dateFormatToUser())
            }
        }
    }

    /*
    * start gallery intent
     */
    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Pilih Foto Profil")
        launcherIntentGallery.launch(chooser)
    }

    /*
    * handle result from gallery intent
     */
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@EditProfilActivity)
                binding.imgFotoProfil.setImageURI(uri)
            }
        }
    }

}