package com.bangkitacademy.medicare.ui.ubahsandi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.core.widget.doOnTextChanged
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.databinding.ActivityUbahSandiBinding

class UbahSandiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUbahSandiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityUbahSandiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolBar.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        init()

        binding.edtSandiBaru.doOnTextChanged { text, _, _, _ ->
            isValidPassword(text)
        }

        binding.edtKonfirmasiSandiBaru.doOnTextChanged { text, _, _, _ ->
            isConfirmedPassword(text)
        }

        binding.btnSimpan.setOnClickListener {
            upload()
        }

    }

    private fun init() {
        binding.edtSandiBaru.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.edtKonfirmasiSandiBaru.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    private fun isConfirmedPassword(text: CharSequence?) {
        val password = binding.edtSandiBaru.text.toString()

        if (text.toString() != password && text.toString().isNotEmpty()) {
            binding.lytKonfirmasiSandiBaru.error = getString(R.string.password_not_match)
        } else {
            binding.lytKonfirmasiSandiBaru.error = null
        }
    }

    private fun isValidPassword(text: CharSequence?) {
        val password = text.toString()
        val regex = Regex("^(?=.*[0-9])(?=\\S+\$)(?=.*[a-z])(?=.*[A-Z]).{8,15}$")

        if (!regex.matches(password) && password.isNotEmpty()) {
            binding.lytSandiBaru.error = if (password.length < 8) {
                getString(R.string.password_too_short)
            } else {
                getString(R.string.invalid_password)
            }
        } else {
            binding.lytSandiBaru.error = null
        }
    }

    private fun upload() {
        val oldPassword = binding.edtSandiLama.text.toString()
        val newPassword = binding.edtSandiBaru.text.toString()
        val confirmedPassword = binding.edtKonfirmasiSandiBaru.text.toString()


        if (isNotEmptyField(oldPassword, newPassword, confirmedPassword)) {
            Log.d("Test", "upload: $oldPassword $newPassword $confirmedPassword")
        }

    }

    private fun isNotEmptyField(
        oldPassword: String,
        newPassword: String,
        confirmedPassword: String
    ): Boolean {
        // check if field is empty
        if (oldPassword.isEmpty()) {
            binding.lytSandiLama.error = getString(R.string.empty_field)
        } else {
            binding.lytSandiLama.error = null
        }

        if (newPassword.isEmpty()) {
            binding.lytSandiBaru.error = getString(R.string.empty_field)
        } else {
            binding.lytSandiBaru.error = null
        }

        if (confirmedPassword.isEmpty()) {
            binding.lytKonfirmasiSandiBaru.error = getString(R.string.empty_field)
        } else {
            binding.lytKonfirmasiSandiBaru.error = null
        }

        return oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmedPassword.isNotEmpty()
    }
}