package com.bangkitacademy.medicare.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.databinding.FragmentVerifyBinding


class VerifyFragment : Fragment() {

    private var _binding: FragmentVerifyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val mLoginFragment = LoginFragment()
            val mFragmentManager = parentFragmentManager

            mFragmentManager.commit {
                replace(
                    R.id.frame_container,
                    mLoginFragment,
                    LoginFragment::class.java.simpleName
                )
                addToBackStack(null)
            }
        }
    }

}