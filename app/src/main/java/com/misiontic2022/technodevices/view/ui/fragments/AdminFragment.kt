package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.core.*
import com.misiontic2022.technodevices.databinding.FragmentAdminBinding
import com.misiontic2022.technodevices.model.remote.AuthDataSource
import com.misiontic2022.technodevices.model.remote.ProfileDataSource
import com.misiontic2022.technodevices.viewModel.domain.auth.AuthRepoImpl
import com.misiontic2022.technodevices.viewModel.domain.profile.ProfileRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.auth.AuthViewModel
import com.misiontic2022.technodevices.viewModel.presentation.auth.AuthViewModelFactory
import com.misiontic2022.technodevices.viewModel.presentation.profile.ProfileViewModel
import com.misiontic2022.technodevices.viewModel.presentation.profile.ProfileViewModelFactory

class AdminFragment : Fragment(R.layout.fragment_admin) {

    private lateinit var binding: FragmentAdminBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(AuthDataSource())
        )
    }
    private val viewModelProfileData by viewModels<ProfileViewModel> {
        ProfileViewModelFactory(
            ProfileRepoImpl(ProfileDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminBinding.bind(view)
        getData()
        binding.buttonEditAdmin.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_adminDetailFragmentDialog)
        }
        binding.buttonSignOut.setOnClickListener {
            signOut()
        }

    }

    private fun getData() {
        viewModelProfileData.getProfileData().observe(viewLifecycleOwner, {result->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    binding.name.text = result.data.name
                    binding.email.text = result.data.email
                    binding.phone.text = result.data.phone
                    binding.address.text = result.data.address
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun signOut() {
        viewModel.signOut().observe(viewLifecycleOwner, {
            when (it) {
                is Result.Loading -> {
                    binding.buttonSignOut.isEnabled = false
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    findNavController().navigate(R.id.loginFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    binding.buttonSignOut.isEnabled = true
                    Toast.makeText(requireContext(), "Error ${it.exception}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }
}
