package com.misiontic2022.technodevices.view.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.core.hide
import com.misiontic2022.technodevices.core.show
import com.misiontic2022.technodevices.databinding.FragmentEditProductBinding
import com.misiontic2022.technodevices.model.models.Product
import com.misiontic2022.technodevices.model.models.User
import com.misiontic2022.technodevices.model.remote.ProductDataSource
import com.misiontic2022.technodevices.viewModel.domain.product.ProductRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.product.ProductViewModel
import com.misiontic2022.technodevices.viewModel.presentation.product.ProductViewModelFactory

class EditProductFragment : Fragment(R.layout.fragment_edit_product) {

    private val viewModel by viewModels<ProductViewModel> {
        ProductViewModelFactory(ProductRepoImpl(ProductDataSource()))
    }
    private lateinit var binding: FragmentEditProductBinding
    private val REQUEST_IMAGE_ALBUM = 1
    private var bitmap: Bitmap? = null
    private val args: OrderDetailDialogFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProductBinding.bind(view)

        getData()
        binding.ivProductImage.setOnClickListener { takeImage() }
        binding.buttonEditProduct.setOnClickListener {
            setData()
        }
    }

    private fun getData() {
        viewModel.getProductData("V05XY0adb7babSAdS3uX4jOCJND2").observe(viewLifecycleOwner, {result->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    Glide.with(this).load(args.photo).fitCenter().into(binding.ivProductImage)
                    binding.etProductName.setText(result.data.title)
                    binding.etProductPrice.setText(result.data.price)
                    binding.etProductDescription.setText(result.data.description)


                }
                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun takeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        try {
            startActivityForResult(intent, REQUEST_IMAGE_ALBUM)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "No se encontro ninguna app para abrir la camara",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setData() {
        val product: Product = Product()
        product.title = binding.etProductName.text.toString()
        product.price = binding.etProductPrice.text.toString()
        product.description = binding.etProductDescription.text.toString()

        if (!validateCredentials(product.title,product.price,product.description,)){

            bitmap?.let { bitmap ->
                viewModel.setProductData(bitmap, product).observe(viewLifecycleOwner, {
                    when (it) {
                        is Result.Loading -> {
                            binding.progressBar.show()
                            Toast.makeText(requireContext(), "Subiendo foto", Toast.LENGTH_LONG)
                                .show()
                            binding.etProductName.isEnabled = false
                            binding.etProductPrice.isEnabled = false
                            binding.etProductDescription.isEnabled = false
                        }
                        is Result.Success -> {
                            findNavController().navigateUp()
                            binding.progressBar.hide()
                        }
                        is Result.Failure -> {
                            binding.progressBar.hide()
                            Toast.makeText(
                                requireContext(),
                                "Error: ${it.exception}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            }

        }


    }


    private fun validateCredentials(nameProduct: String, priceProduct: String, descriptionProduct: String):Boolean {
        if (nameProduct.isEmpty()) {
            binding.etProductName.error = "Nombre of product is empty"
            return true
        }
        if (priceProduct.isEmpty()) {
            binding.etProductPrice.error = "Price is empty"
            return true
        }
        if (descriptionProduct.isEmpty()) {
            binding.etProductDescription.error = "Description is empty"
            return true
        }
        return false
    }

}