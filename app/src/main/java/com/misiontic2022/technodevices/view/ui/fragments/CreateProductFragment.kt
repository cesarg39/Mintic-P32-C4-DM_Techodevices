package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.databinding.FragmentCreateProductBinding


class CreateProductFragment : Fragment(R.layout.fragment_create_product) {
    private lateinit var binding: FragmentCreateProductBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateProductBinding.bind(view)
    }
}