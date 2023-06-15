package com.bangkitacademy.medicare.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.data.remote.request.ResendRequest
import com.bangkitacademy.medicare.databinding.FragmentVerifyBinding
import com.bangkitacademy.medicare.utils.ViewModelFactory
import com.bangkitacademy.medicare.utils.Result


class VerifyFragment : Fragment() {

    private var _binding: FragmentVerifyBinding? = null
    private val binding get() = _binding!!
    private val verifyViewModel by viewModels<VerifyViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

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
        val email = arguments?.getString(EXTRA_EMAIL)

        binding.tvResend.setOnClickListener {
            val body = ResendRequest(email)
            verifyViewModel.setResendInfo(body)
            verifyViewModel.resend().observe(requireActivity()) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.loginButton.isEnabled = false
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.loginButton.isEnabled = true
                        Toast.makeText(
                            requireContext(),
                            "Resend Success",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.loginButton.isEnabled = true
                        Toast.makeText(
                            requireContext(),
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

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

    companion object {
        const val EXTRA_EMAIL = "extra_email"
    }
}