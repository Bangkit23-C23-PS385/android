package com.bangkitacademy.medicare

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkitacademy.medicare.data.remote.request.CreateProfileRequest
import com.bangkitacademy.medicare.databinding.ActivityMainBinding
import com.bangkitacademy.medicare.ui.profil.ProfilViewModel
import com.bangkitacademy.medicare.utils.Result
import com.bangkitacademy.medicare.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<ProfilViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val requestCreateProfile = CreateProfileRequest(name)

        viewModel.createProfile(requestCreateProfile).observe(this) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {}
                is Result.Error -> {}
            }
        }

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_beranda, R.id.navigation_prediction, R.id.navigation_profil
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
    }
}