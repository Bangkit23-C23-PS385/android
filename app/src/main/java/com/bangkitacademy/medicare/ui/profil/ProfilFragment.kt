package com.bangkitacademy.medicare.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.data.remote.response.ProfileData
import com.bangkitacademy.medicare.databinding.FragmentProfilBinding
import com.bangkitacademy.medicare.ui.auth.AuthenticationActivity
import com.bangkitacademy.medicare.ui.editprofil.EditProfilActivity
import com.bangkitacademy.medicare.utils.Result
import com.bangkitacademy.medicare.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private lateinit var profileData: ProfileData

    private val binding get() = _binding!!
    private val profilViewModel by viewModels<ProfilViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfilBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilViewModel.getProfile().observe(requireActivity()) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    profileData = result.data.data!!
                    setupProfile(result.data.data)
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        result.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.editProfile.setOnClickListener {
            moveToEditProfile(profileData)
        }

        binding.ubahSandi.setOnClickListener {
            showFiturBelumTersedia()
        }

        binding.statusKesehatan.setOnClickListener {
            showFiturBelumTersedia()
        }

        binding.indeksMassaTubuh.setOnClickListener {
            showFiturBelumTersedia()
        }

        binding.btnKeluar.setOnClickListener {
            profilViewModel.logout()
            val intent = Intent(requireActivity(), AuthenticationActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun moveToEditProfile(profileData: ProfileData) {
        val intent = Intent(requireActivity(), EditProfilActivity::class.java)
        intent.putExtra(EXTRA_PROFILE, profileData)
        startActivity(intent)
    }

    private fun setupProfile(user: ProfileData) {
        binding.txtNamaProfil.text = user.name
    }

    private fun showFiturBelumTersedia() {
        Snackbar.make(
            requireView(),
            getString(R.string.fitur_belum_tersedia),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onResume() {
        super.onResume()
        profilViewModel.getProfile().observe(requireActivity()) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    profileData = result.data.data!!
                    setupProfile(result.data.data)
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        result.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_PROFILE = "extra_profile"
    }
}