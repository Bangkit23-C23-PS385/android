package com.bangkitacademy.medicare.ui.profil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkitacademy.medicare.databinding.FragmentProfilBinding
import com.bangkitacademy.medicare.ui.editprofil.EditProfilActivity
import com.google.android.material.snackbar.Snackbar

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
//        val profilViewModel =
//            ViewModelProvider(this).get(ProfilViewModel::class.java)

        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editProfile.setOnClickListener {
            val intent = Intent(requireActivity(), EditProfilActivity::class.java)
            startActivity(intent)
        }

        binding.statusKesehatan.setOnClickListener {
            showSnakcBar("Fitur ini belum tersedia")
        }

        binding.indeksMassaTubuh.setOnClickListener {
            showSnakcBar("Fitur ini belum tersedia")
        }

    }

    private fun showSnakcBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}