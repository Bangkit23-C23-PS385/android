package com.bangkitacademy.medicare.ui.editprofil

import android.app.DatePickerDialog
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatRadioButton
import com.bangkitacademy.medicare.MainActivity
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.data.remote.request.UpdateProfileRequest
import com.bangkitacademy.medicare.data.remote.response.ProfileData
import com.bangkitacademy.medicare.databinding.ActivityEditProfilBinding
import com.bangkitacademy.medicare.ui.profil.ProfilFragment.Companion.EXTRA_PROFILE
import com.bangkitacademy.medicare.utils.Result
import com.bangkitacademy.medicare.utils.ViewModelFactory
import com.bangkitacademy.medicare.utils.dateFormatFromDbToUser
import com.bangkitacademy.medicare.utils.dateFormatToDb
import com.bangkitacademy.medicare.utils.uriToFile
import java.io.File
import java.util.Calendar

class EditProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfilBinding
    private val editProfilViewModel by viewModels<EditProfilViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private var birthDate: String = "0001-01-01"
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val profileData = intent.getParcelableExtra<ProfileData>(EXTRA_PROFILE)
        setupProfile(profileData!!)

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
     * setup profile data to view
     */
    private fun setupProfile(profileData: ProfileData) {
        val name = profileData.name
        val dateOfBirth = profileData.dateOfBirth
        val gender = profileData.gender
        val weight = profileData.weight
        val height = profileData.height

        binding.edtNama.setText(name)
        if (dateOfBirth != "0001-01-01") {
            birthDate = dateOfBirth
            binding.edtTanggalLahir.setText(dateOfBirth.dateFormatFromDbToUser())
        }

        if (gender.isNotEmpty()) {
            if (gender == "LAKILAKI") {
                binding.radioLakiLaki.isChecked = true
            } else {
                binding.radioPerempuan.isChecked = true
            }
        }

        if (weight != 0) {
            binding.edtBeratBadan.setText(weight.toString())
        } else {
            binding.edtBeratBadan.setText("")
        }

        if (height != 0) {
            binding.edtTinggiBadan.setText(height.toString())
        } else {
            binding.edtTinggiBadan.setText("")
        }

        Log.d("EditProfilActivity", "setupProfile: ${binding.edtTanggalLahir.text}")
    }

    /*
     * open date picker when date of birth edit text clicked
     */
    private fun openDatePicker() {
        val calendar = Calendar.getInstance()

        val years = calendar.get(Calendar.YEAR)
        val months = calendar.get(Calendar.MONTH)
        val days = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month + 1}/$year"
            birthDate = date.dateFormatToDb()
            binding.edtTanggalLahir.setText(date)
        }, years, months, days)
        datePickerDialog.show()
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
        var tinggiBadan = binding.edtTinggiBadan.text.toString()
        var beratBadan = binding.edtBeratBadan.text.toString()
        var jenisKelamin = ""

        // get selected radio button
        if (selectedJkId != -1) {
            val selectedJk = binding.radioGroupJk.findViewById(selectedJkId) as AppCompatRadioButton
            jenisKelamin = if (selectedJk.text == "Laki-laki") {
                "LAKILAKI"
            } else {
                "PEREMPUAN"
            }
        }

        if (nama.isNotEmpty()) {
            binding.lytNama.error = null

            if (tinggiBadan.isEmpty()) {
                tinggiBadan = "0"
            }

            if (beratBadan.isEmpty()) {
                beratBadan = "0"
            }

            val updateDataProfile = UpdateProfileRequest(
                name = nama,
                gender = jenisKelamin,
                dateOfBirth = tanggalLahir,
                height = tinggiBadan.toInt(),
                weight = beratBadan.toInt()
            )

            editProfilViewModel.updateProfile(updateDataProfile).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        finish()
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        } else {
            binding.lytNama.requestFocus()
            binding.lytNama.error = getString(R.string.empty_field)
        }

//        if (nama.isEmpty() || jenisKelamin.isEmpty() || tanggalLahir.isNullOrEmpty() || tinggiBadan.isEmpty() || beratBadan.isEmpty()) {
//            if (nama.isEmpty()) {
//                binding.lytNama.requestFocus()
//                binding.lytNama.error = getString(R.string.empty_field)
//            } else {
//                binding.lytNama.error = null
//            }
//
//            if (tanggalLahir.isNullOrEmpty()) {
//                binding.edtTanggalLahir.requestFocus()
//                binding.lytTanggalLahir.error = getString(R.string.empty_field)
//            } else {
//                binding.lytTanggalLahir.error = null
//            }
//
//            if (tinggiBadan.isEmpty()) {
//                binding.lytTinggiBadan.requestFocus()
//                binding.lytTinggiBadan.error = getString(R.string.empty_field)
//            } else {
//                binding.lytTinggiBadan.error = null
//            }
//
//            if (beratBadan.isEmpty()) {
//                binding.lytBeratBadan.requestFocus()
//                binding.lytBeratBadan.error = getString(R.string.empty_field)
//            } else {
//                binding.lytBeratBadan.error = null
//            }
//
//            if (jenisKelamin.isEmpty()) {
//                Toast.makeText(this, "Jenis kelamin tidak boleh kosong", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            Log.d(
//                "Test",
//                "uploadData: $nama, $jenisKelamin, $tanggalLahir, $tinggiBadan, $beratBadan $getFile"
//            )
//        }
    }

}