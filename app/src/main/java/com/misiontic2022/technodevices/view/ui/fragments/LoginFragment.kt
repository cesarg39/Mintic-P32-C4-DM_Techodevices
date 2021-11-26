package com.misiontic2022.technodevices.view.ui.fragments

import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.databinding.FragmentLoginBinding
import com.misiontic2022.technodevices.model.remote.AuthDataSource
import com.misiontic2022.technodevices.viewModel.domain.auth.AuthRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.auth.AuthViewModel
import com.misiontic2022.technodevices.viewModel.presentation.auth.AuthViewModelFactory
import com.misiontic2022.technodevices.core.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(AuthDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
        goToSignUpPage()
    }


    private fun isUserLoggedIn() {
        firebaseAuth.currentUser?.let { user ->
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun doLogin() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            validateCredentials(email, password)
            signIn(email, password)
        }

    }

    private fun goToSignUpPage() {
        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment2)
        }
    }

    private fun validateCredentials(email: String, password: String) {
        if (email.isEmpty()) {
            binding.etEmail.error = "E-Mail is empty"
            return
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is empty"
            return
        }
    }

    private fun signIn(email: String, password: String) {
        viewModel.signIn(email, password).observe(viewLifecycleOwner, {
            when (it) {
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignIn.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.btnSignIn.isEnabled = true
                    Toast.makeText(requireContext(), "Error ${it.exception}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }
}