package com.bangkitacademy.medicare.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.bangkitacademy.medicare.MainActivity
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.data.preferences.UserModel
import com.bangkitacademy.medicare.data.remote.retrofit.ApiConfig
import com.bangkitacademy.medicare.databinding.ActivitySplashScreenBinding
import com.bangkitacademy.medicare.ui.auth.AuthenticationActivity
import com.bangkitacademy.medicare.ui.auth.AuthenticationViewModel
import com.bangkitacademy.medicare.utils.ViewModelFactory

class SplashScreenActivity : AppCompatActivity() {

    private val authenticationViewModel by viewModels<AuthenticationViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        setupAction()
    }

    private fun setupAction() {
        supportActionBar?.hide()
        authenticationViewModel.getUser().observe(this) { user: UserModel ->
            ApiConfig.token = user.token
            if (user.token.isNotEmpty()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }, DELAY)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    val authIntent =
                        Intent(this@SplashScreenActivity, AuthenticationActivity::class.java)
                    startActivity(authIntent)
                    finish()
                }, DELAY)
            }
        }
    }

    companion object {
        const val DELAY = 2000L
    }
}