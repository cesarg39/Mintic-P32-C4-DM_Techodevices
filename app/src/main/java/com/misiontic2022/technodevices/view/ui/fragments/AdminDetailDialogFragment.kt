package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.core.hide
import com.misiontic2022.technodevices.core.show
import com.misiontic2022.technodevices.databinding.FragmentAdminDetailDialogBinding
import com.misiontic2022.technodevices.model.models.User
import com.misiontic2022.technodevices.model.remote.ProfileDataSource
import com.misiontic2022.technodevices.viewModel.domain.profile.ProfileRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.profile.ProfileViewModel
import com.misiontic2022.technodevices.viewModel.presentation.profile.ProfileViewModelFactory


class AdminDetailDialogFragment : Fragment(R.layout.fragment_admin_detail_dialog) {

    private val viewModelProfileData by viewModels<ProfileViewModel> {
        ProfileViewModelFactory(ProfileRepoImpl(ProfileDataSource()))
    }

    private lateinit var binding: FragmentAdminDetailDialogBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAdminDetailDialogBinding.bind(view)

        getData()

        binding.btnEditProfile.setOnClickListener {
            setData()
        }
    }

    private fun setData() {
        val name = binding.etNameAdmin.text.toString()
        val address = binding.etAddressAdmin.text.toString()
        val phone = binding.etPhoneAdmin.text.toString()
        val email = binding.etEmailAdmin.text.toString()

        if (!validateCredentials(name,address,phone,email)){
            val profileUser = User(name = name,address = address,phone = phone, email = email)

            viewModelProfileData.setProfileData(profileUser).observe(viewLifecycleOwner, {
                    result->
                when(result) {
                    is Result.Loading ->{
                        binding.progressBar.show()
                        binding.etNameAdmin.isEnabled = false
                        binding.etAddressAdmin.isEnabled = false
                        binding.etPhoneAdmin.isEnabled = false
                    }
                    is Result.Success ->{
                        findNavController().navigateUp()
                    }
                    is Result.Failure ->{
                        Toast.makeText(requireContext(),"Error ${result.exception}", Toast.LENGTH_SHORT).show()
                        binding.progressBar.hide()
                    }
                }
            })
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
                    binding.etNameAdmin.setText(result.data.name)
                    binding.etEmailAdmin.setText(result.data.email)
                    binding.etPhoneAdmin.setText(result.data.phone)
                    binding.etAddressAdmin.setText(result.data.address)
                    binding.etEmailAdmin.isEnabled = false
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

    }

    private fun validateCredentials(name: String, address: String, phone: String, email: String):Boolean {
        if (name.isEmpty()) {
            binding.etNameAdmin.error = "E-Mail is empty"
            return true
        }
        if (address.isEmpty()) {
            binding.etAddressAdmin.error = "Address is empty"
            return true
        }
        if (phone.isEmpty()) {
            binding.etPhoneAdmin.error = "Phone is empty"
            return true
        }
        if (email.isEmpty()) {
            binding.etEmailAdmin.error = "Email is empty"
            return true
        }
        return false
    }



}

