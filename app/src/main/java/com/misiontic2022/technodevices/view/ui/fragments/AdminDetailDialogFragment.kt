package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.databinding.FragmentAdminDetailDialogBinding


class AdminDetailDialogFragment : Fragment(R.layout.fragment_admin_detail_dialog) {
    private lateinit var binding: FragmentAdminDetailDialogBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminDetailDialogBinding.bind(view)
    }
}