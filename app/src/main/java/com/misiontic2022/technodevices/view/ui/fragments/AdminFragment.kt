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
import com.misiontic2022.technodevices.viewModel.domain.auth.AuthRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.auth.AuthViewModel
import com.misiontic2022.technodevices.viewModel.presentation.auth.AuthViewModelFactory

class AdminFragment : Fragment(R.layout.fragment_admin) {

    private lateinit var binding: FragmentAdminBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(AuthDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminBinding.bind(view)
        binding.buttonEditAdmin.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_adminDetailFragmentDialog)
        } 

        binding.buttonSignOut.setOnClickListener {
            viewModel.signOut().observe(viewLifecycleOwner, {
                when (it) {
                    is Result.Loading -> {
                        binding.buttonSignOut.isEnabled = false
                        binding.progressBar.show()
                    }
                    is Result.Success -> {
                        binding.progressBar.hide()
                        findNavController().navigate(R.id.action_adminFragment_to_loginFragment)
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
}
