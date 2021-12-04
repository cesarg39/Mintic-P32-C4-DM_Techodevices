package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.databinding.FragmentAddProductBinding

class AddCommentFragment : Fragment(R.layout.add_comment_fragment) {

    private lateinit var binding: FragmentAddProductBinding
    private val args:

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProductBinding.bind(view)

    }

}