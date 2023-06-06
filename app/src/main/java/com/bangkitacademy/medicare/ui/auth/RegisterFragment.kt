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
import com.bangkitacademy.medicare.data.remote.request.RegisterRequest
import com.bangkitacademy.medicare.databinding.FragmentRegisterBinding
import com.bangkitacademy.medicare.utils.ViewModelFactory
import com.bangkitacademy.medicare.utils.Result
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() {
        binding.tvLogin.setOnClickListener {
            val mFragmentManager = parentFragmentManager
            mFragmentManager.popBackStack()
        }

        binding.registerButton.setOnClickListener {
            val edEmail = binding.edEmail.text.toString()
            val edName = binding.edName.text.toString()
            val edUsername = binding.edUsername.text.toString()
            val edPassword = binding.edPassword.text.toString()
            val edConfirmPassword = binding.edConfirmPassword.text.toString()

            val isValid =
                binding.edName.error == null && binding.edEmail.error == null && binding.edUsername.error == null && binding.edPassword.error == null && binding.edConfirmPassword.error == null

            when {
                edEmail.isEmpty() -> {
                    binding.edEmail.error = getString(R.string.email_empty)
                }

                !android.util.Patterns.EMAIL_ADDRESS.matcher(edEmail).matches() -> {
                    binding.edEmail.error = getString(R.string.invalid_email)
                }

                edName.isEmpty() -> {
                    binding.edName.error = getString(R.string.name_empty)
                }

                edUsername.isEmpty() -> {
                    binding.edUsername.error = getString(R.string.username_empty)
                }

                edUsername.length < 5 -> {
                    binding.edUsername.error = getString(R.string.username_too_short)
                }

                edPassword.isEmpty() -> {
                    binding.edPassword.error = getString(R.string.password_empty)
                }

                edPassword.length !in 8..15 || !Pattern.compile("^(?=.*\\d)(?=\\S+$).{8,}$").matcher(edPassword).matches() -> {
                    binding.edPassword.error = if (edPassword.length !in 8..15) {
                        getString(R.string.password_length)
                    } else {
                        getString(R.string.invalid_password)
                    }
                }

                edConfirmPassword.isEmpty() -> {
                    binding.edConfirmPassword.error = getString(R.string.confirm_password_empty)
                }

                edPassword != edConfirmPassword -> {
                    binding.edConfirmPassword.error = getString(R.string.confirm_password_not_match)
                }

                isValid -> {
                    val body = RegisterRequest(
                        email = edEmail,
                        name = edName,
                        username = edUsername,
                        password = edPassword
                    )

                    registerViewModel.setRegisterInfo(body)
                    registerViewModel.register().observe(requireActivity()) { result ->
                        when (result) {
                            is Result.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.registerButton.isEnabled = false
                            }
                            is Result.Success -> {
                                binding.registerButton.isEnabled = true
                                val mVerifyFragment = VerifyFragment()
                                val mFragmentManager = parentFragmentManager
                                mFragmentManager.commit {
                                    replace(
                                        R.id.frame_container,
                                        mVerifyFragment,
                                        VerifyFragment::class.java.simpleName)
                                    addToBackStack(null)
                                }
                                Toast.makeText(
                                    context,
                                    "Register Success",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is Result.Error -> {
                                binding.progressBar.visibility = View.GONE
                                binding.registerButton.isEnabled = true
                                Toast.makeText(
                                    context,
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