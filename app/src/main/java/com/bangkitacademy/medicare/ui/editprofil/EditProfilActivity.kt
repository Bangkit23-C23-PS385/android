package com.bangkitacademy.medicare.ui.editprofil

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatRadioButton
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.databinding.ActivityEditProfilBinding
import com.bangkitacademy.medicare.utils.dateFormatToDb
import com.bangkitacademy.medicare.utils.dateFormatToUser
import com.bangkitacademy.medicare.utils.uriToFile
import com.google.android.material.datepicker.MaterialDatePicker
import java.io.File

class EditProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfilBinding
    private var birthDate: String? = null
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // handle top app bar back button
        binding.toolBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        // handle button edit foto
        binding.lytFotoProfil.setOnClickListener {
            startGallery()
        }

        binding.btnSimpan.setOnClickListener {
            uploadData()
        }

        // handle date picker
        binding.edtTanggalLahir.setOnClickListener {
            openDatePicker()
        }

    }

    /*
     * open date picker when date of birth edit text clicked
     */
    private fun openDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Pilih Tanggal Lahir")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()

        datePicker.show(supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            birthDate = datePicker.headerText.dateFormatToDb()
            binding.edtTanggalLahir.setText(datePicker.headerText.dateFormatToUser())
            binding.edtTanggalLahir.error = null
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
                getFile = myFile
                binding.imgFotoProfil.setImageURI(uri)
            }
        }
    }

    /*
     * upload new data user to database
     */
    private fun uploadData() {
        val nama = binding.edtNama.text.toString()
        val selectedJkId = binding.radioGroupJk.checkedRadioButtonId
        val tanggalLahir = birthDate
        val tinggiBadan = binding.edtTinggiBadan.text.toString()
        val beratBadan = binding.edtBeratBadan.text.toString()
        var jenisKelamin = ""

        // get selected radio button
        if (selectedJkId != -1) {
            val selectedJk = binding.radioGroupJk.findViewById(selectedJkId) as AppCompatRadioButton
            jenisKelamin = if (selectedJk.text == "Laki-laki") {
                "L"
            } else {
                "P"
            }
        }

        if (nama.isEmpty() || jenisKelamin.isEmpty() || tanggalLahir.isNullOrEmpty() || tinggiBadan.isEmpty() || beratBadan.isEmpty()) {
            if (nama.isEmpty()) {
                binding.lytNama.requestFocus()
                binding.lytNama.error = getString(R.string.empty_field)
            } else {
                binding.lytNama.error = null
            }

            if (tanggalLahir.isNullOrEmpty()) {
                binding.edtTanggalLahir.requestFocus()
                binding.lytTanggalLahir.error = getString(R.string.empty_field)
            } else {
                binding.lytTanggalLahir.error = null
            }

            if (tinggiBadan.isEmpty()) {
                binding.lytTinggiBadan.requestFocus()
                binding.lytTinggiBadan.error = getString(R.string.empty_field)
            } else {
                binding.lytTinggiBadan.error = null
            }

            if (beratBadan.isEmpty()) {
                binding.lytBeratBadan.requestFocus()
                binding.lytBeratBadan.error = getString(R.string.empty_field)
            } else {
                binding.lytBeratBadan.error = null
            }

            if (jenisKelamin.isEmpty()) {
                Toast.makeText(this, "Jenis kelamin tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d(
                "Test",
                "uploadData: $nama, $jenisKelamin, $tanggalLahir, $tinggiBadan, $beratBadan $getFile"
            )
        }
    }

}