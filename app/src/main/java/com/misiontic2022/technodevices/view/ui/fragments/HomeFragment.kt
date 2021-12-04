package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.databinding.FragmentHomeBinding
import com.misiontic2022.technodevices.model.models.Product
import com.misiontic2022.technodevices.model.remote.ProductDataSource
import com.misiontic2022.technodevices.view.ui.fragments.adapters.ProductAdapter
import com.misiontic2022.technodevices.viewModel.domain.product.ProductRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.product.ProductViewModel
import com.misiontic2022.technodevices.viewModel.presentation.product.ProductViewModelFactory
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import com.misiontic2022.technodevices.core.*
import com.misiontic2022.technodevices.model.remote.ProfileDataSource
import com.misiontic2022.technodevices.viewModel.domain.profile.ProfileRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.profile.ProfileViewModel
import com.misiontic2022.technodevices.viewModel.presentation.profile.ProfileViewModelFactory

class HomeFragment : Fragment(R.layout.fragment_home), ProductAdapter.OnProductClickListener {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<ProductViewModel> {
        ProductViewModelFactory(ProductRepoImpl(ProductDataSource()))
    }

    private val viewModelProfileData by viewModels<ProfileViewModel> {
        ProfileViewModelFactory(
            ProfileRepoImpl(ProfileDataSource())
        )
    }

    private val list = mutableListOf<CarouselItem>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        list.add(CarouselItem("https://technodevices-fe.herokuapp.com/img/banner1.059f4c55.png"))
        list.add(CarouselItem("https://technodevices-fe.herokuapp.com/img/banner2.36a00eb9.png"))
        list.add(CarouselItem("https://technodevices-fe.herokuapp.com/img/banner3.d13afcf5.png"))
        viewModel.getLatestProduct().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    binding.rvProducts.adapter = ProductAdapter(result.data, this@HomeFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(
                        requireContext(), "Error: ${result.exception.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
        binding.carouselProduct.addData(list)

    }



    override fun onProductClick(product: Product) {
        Log.d("Movie", "onProductClick: $product")
        val action = HomeFragmentDirections.actionHomeFragmentToOrderDetailDialogFragment(product.photo,product.title,product.price,product.description,product.id, product.uid)
        findNavController().navigate(action)
    }



}