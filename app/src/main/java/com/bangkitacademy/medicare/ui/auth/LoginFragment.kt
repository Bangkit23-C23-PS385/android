package com.bangkitacademy.medicare.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bangkitacademy.medicare.MainActivity
import com.bangkitacademy.medicare.R
import com.bangkitacademy.medicare.data.remote.request.LoginRequest
import com.bangkitacademy.medicare.databinding.FragmentLoginBinding
import com.bangkitacademy.medicare.utils.ViewModelFactory
import com.bangkitacademy.medicare.utils.Result

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        binding.tvRegister.setOnClickListener {
            val mRegisterFragment = RegisterFragment()
            val mFragmentManager = parentFragmentManager

            mFragmentManager.commit {
                replace(
                    R.id.frame_container,
                    mRegisterFragment,
                    RegisterFragment::class.java.simpleName
                )
                addToBackStack(null)
            }
        }

        binding.loginButton.setOnClickListener {
            val edEmail = binding.edEmail.text.toString()
            val edPassword = binding.edPassword.text.toString()
            val isValid = binding.edEmail.error == null && binding.edPassword.error == null

            when {
                edEmail.isEmpty() -> {
                    binding.edEmail.error = getString(R.string.email_or_username_empty)
                }
                edPassword.isEmpty() -> {
                    binding.edPassword.error = getString(R.string.password_empty)
                }
                isValid -> {
                    val body = LoginRequest(
                        identifier = edEmail,
                        password = edPassword
                    )

                    loginViewModel.setLoginInfo(body)
                    loginViewModel.login().observe(requireActivity()) { result ->
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.loginButton.isEnabled = false
                            }
                            is Result.Success -> {
                                binding.loginButton.isEnabled = true
                                activity?.let {
                                    val intent = Intent(it, MainActivity::class.java)
                                    intent.putExtra(MainActivity.EXTRA_NAME, result.data.data?.name)
                                    startActivity(intent)
                                    it.finish()
                                }
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
            }
        }
    }
}