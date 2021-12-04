package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
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
import com.misiontic2022.technodevices.model.remote.CommentDataSource
import com.misiontic2022.technodevices.model.remote.ProfileDataSource
import com.misiontic2022.technodevices.view.ui.fragments.adapters.CommentAdapter
import com.misiontic2022.technodevices.view.ui.fragments.adapters.ProductAdapter
import com.misiontic2022.technodevices.viewModel.domain.comment.CommentRepoImpl
import com.misiontic2022.technodevices.viewModel.domain.profile.ProfileRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.comment.CommentViewModel
import com.misiontic2022.technodevices.viewModel.presentation.comment.CommentViewModelFactory
import com.misiontic2022.technodevices.viewModel.presentation.profile.ProfileViewModel
import com.misiontic2022.technodevices.viewModel.presentation.profile.ProfileViewModelFactory


class OrderDetailDialogFragment : Fragment(R.layout.fragment_order_detail_dialog) {

    private lateinit var binding: FragmentOrderDetailDialogBinding

    private val args: OrderDetailDialogFragmentArgs by navArgs()

    private val viewModelProfileData by viewModels<ProfileViewModel> {
        ProfileViewModelFactory(
            ProfileRepoImpl(ProfileDataSource())
        )
    }
    private val viewModelComments by viewModels<CommentViewModel> {
        CommentViewModelFactory(CommentRepoImpl(CommentDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOrderDetailDialogBinding.bind(view)
        getData()
        getSellerData()
        getComments()
    }

    private fun getComments() {
        viewModelComments.getComments(args.id).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    binding.rvItemComments.adapter = CommentAdapter(result.data)
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_LONG)
                        .show()
                    println(result.exception)
                }
            }
        })
    }

    private fun getData() {
        //binding.progressBar.show()
        Glide.with(this).load(args.photo).fitCenter().into(binding.imgProductData)
        binding.tvProductTitle.text = args.title
        binding.tvProductPrice.text = args.price
        binding.tvProductDescription.text = args.description
        binding.btnAddComments.setOnClickListener {
            val action = OrderDetailDialogFragmentDirections.actionOrderDetailDialogFragmentToAddCommentFragment(args.id)
            findNavController().navigate(action)
        }
    }


    private fun getSellerData(uid: String = args.uid) {
        viewModelProfileData.getProfileSellerData(uid).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    binding.tvUserEmail.text = result.data.email
                    binding.tvUserPhone.text = result.data.phone
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_LONG)
                        .show()
                    println(result.exception)
                }
            }
        })

    }
}