package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.core.hide
import com.misiontic2022.technodevices.core.show
import com.misiontic2022.technodevices.databinding.FragmentCreateProductBinding
import com.misiontic2022.technodevices.model.models.Product
import com.misiontic2022.technodevices.model.remote.ProductDataSource
import com.misiontic2022.technodevices.view.ui.fragments.adapters.MyProductsAdapter
import com.misiontic2022.technodevices.view.ui.fragments.adapters.ProductAdapter
import com.misiontic2022.technodevices.viewModel.domain.product.ProductRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.product.ProductViewModel
import com.misiontic2022.technodevices.viewModel.presentation.product.ProductViewModelFactory


class CreateProductFragment : Fragment(R.layout.fragment_create_product),MyProductsAdapter.OnProductClickListener {
    private lateinit var binding: FragmentCreateProductBinding
    private val viewModel by viewModels<ProductViewModel>{
        ProductViewModelFactory(ProductRepoImpl(ProductDataSource()))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateProductBinding.bind(view)
        viewModel.getLatestProduct(true).observe(viewLifecycleOwner, {result->
            when(result){
                is Result.Loading->{
                    binding.progressBar.show()
                }
                is Result.Success->{
                    binding.progressBar.hide()
                    binding.rvProducts.adapter = MyProductsAdapter(result.data,this@CreateProductFragment)
                }
                is Result.Failure->{
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(),"Error: ${result.exception.localizedMessage}",
                        Toast.LENGTH_LONG).show()
                }
            }
        })
        binding.buttonAddProduct.setOnClickListener {
            findNavController().navigate(R.id.action_createProductFragment_to_addProductFragment)
        }
    }

    override fun onProductClick(product: Product, action: Int) {

    }
}