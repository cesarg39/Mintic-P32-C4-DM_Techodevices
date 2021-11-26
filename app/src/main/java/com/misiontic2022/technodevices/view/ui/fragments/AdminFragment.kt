package com.misiontic2022.technodevices.view.ui.fragments

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.core.hide
import com.misiontic2022.technodevices.databinding.FragmentAdminBinding

class AdminFragment : Fragment(R.layout.fragment_admin) {

    private lateinit var binding: FragmentAdminBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminBinding.bind(view)
        binding.buttonEditAdmin.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_adminDetailFragmentDialog)
        }

        binding.buttonLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_adminFragment_to_loginFragment)
        }
    }
}