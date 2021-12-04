package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.core.hide
import com.misiontic2022.technodevices.core.show
import com.misiontic2022.technodevices.databinding.FragmentOrderDetailDialogBinding
import com.misiontic2022.technodevices.model.remote.ProductDataSource
import com.misiontic2022.technodevices.viewModel.domain.product.ProductRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.product.ProductViewModel
import com.misiontic2022.technodevices.viewModel.presentation.product.ProductViewModelFactory
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide


class OrderDetailDialogFragment : Fragment(R.layout.fragment_order_detail_dialog) {

    private lateinit var binding: FragmentOrderDetailDialogBinding

    private val args: OrderDetailDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderDetailDialogBinding.bind(view)
        getData()
    }

    private fun getData() {
        //binding.progressBar.show()
        Glide.with(this).load(args.photo).fitCenter().into(binding.imgProductData)
        binding.tvProductTitle.text = args.title
        binding.tvProductPrice.text = args.price
        binding.tvProductDescription.text = args.description

    }
}