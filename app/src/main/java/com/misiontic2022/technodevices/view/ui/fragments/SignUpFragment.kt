package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.databinding.FragmentSignUpBinding
import com.misiontic2022.technodevices.model.remote.AuthDataSource
import com.misiontic2022.technodevices.viewModel.domain.auth.AuthRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.auth.AuthViewModel
import com.misiontic2022.technodevices.viewModel.presentation.auth.AuthViewModelFactory
import com.misiontic2022.technodevices.core.*

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(AuthDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        signUp()
    }

    private fun signUp() {
        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPass.text.toString().trim()

            if (validUserData(
                    name,
                    password,
                    confirmPassword,
                    email,
                    address,
                    phone
                )
            ) return@setOnClickListener

            createUser(email, password, name, address, phone)
        }
    }

    private fun createUser(
        email: String,
        password: String,
        name: String,
        address: String,
        phone: String
    ) {
        viewModel.signUp(email, password, name, address, phone)
            .observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.show()
                        binding.btnSignUp.isEnabled = false
                    }
                    is Result.Failure -> {
                        binding.progressBar.hide()
                        binding.btnSignUp.isEnabled = true
                        Toast.makeText(
                            requireContext(),
                            "Error ${result.exception}",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                    is Result.Success -> {
                        binding.progressBar.hide()
                        findNavController().navigate(R.id.action_signUpFragment2_to_homeFragment)
                    }
                }
            })
    }

    private fun validUserData(
        name: String,
        password: String,
        confirmPassword: String,
        email: String,
        address: String,
        phone: String
    ): Boolean {
        when {
            name.isEmpty() -> {
                binding.etName.error = "El nombre esta vacio"
                return true
            }
            email.isEmpty() -> {
                binding.etEmail.error = "email esta vacio"
                return true
            }
            address.isEmpty() -> {
                binding.etEmail.error = "Direccion esta vacia"
                return true
            }
            phone.isEmpty() -> {
                binding.etEmail.error = "telefono esta vacia"
                return true
            }
            password.isEmpty() -> {
                binding.etPassword.error = "Contraseña esta vacia"
                return true
            }
            confirmPassword.isEmpty() -> {
                binding.etConfirmPass.error = "Contraseña esta vacia"
                return true
            }
            password != confirmPassword -> {
                binding.etConfirmPass.error = "Las contraseñas no coinciden"
                binding.etPassword.error = "Las contraseñas no coinciden"
                return true
            }
        }
        return false
    }
}