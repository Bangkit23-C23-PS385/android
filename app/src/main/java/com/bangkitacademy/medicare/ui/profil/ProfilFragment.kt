package com.bangkitacademy.medicare.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.databinding.FragmentProfilBinding
import com.bangkitacademy.medicare.ui.auth.AuthenticationActivity
import com.bangkitacademy.medicare.ui.editprofil.EditProfilActivity
import com.bangkitacademy.medicare.ui.ubahsandi.UbahSandiActivity
import com.bangkitacademy.medicare.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val profilViewModel by viewModels<ProfilViewModel>{
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
//        val profilViewModel =
//            ViewModelProvider(this).get(ProfilViewModel::class.java)

        _binding = FragmentProfilBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editProfile.setOnClickListener {
            val intent = Intent(requireActivity(), EditProfilActivity::class.java)
            startActivity(intent)
        }

        binding.ubahSandi.setOnClickListener {
            val intent = Intent(requireActivity(), UbahSandiActivity::class.java)
            startActivity(intent)
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

    private fun showFiturBelumTersedia() {
        Snackbar.make(requireView(), getString(R.string.fitur_belum_tersedia), Snackbar.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}