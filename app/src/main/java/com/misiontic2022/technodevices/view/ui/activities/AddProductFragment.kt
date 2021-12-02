package com.misiontic2022.technodevices.view.ui.activities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.databinding.FragmentAddProductBinding
import com.misiontic2022.technodevices.model.models.Product
import com.misiontic2022.technodevices.model.remote.ProductDataSource
import com.misiontic2022.technodevices.viewModel.domain.product.ProductRepoImpl
import com.misiontic2022.technodevices.viewModel.presentation.product.ProductViewModel
import com.misiontic2022.technodevices.viewModel.presentation.product.ProductViewModelFactory
import com.misiontic2022.technodevices.core.*

class AddProductFragment : Fragment(R.layout.fragment_add_product) {
    private val viewModel by viewModels<ProductViewModel> {
        ProductViewModelFactory(ProductRepoImpl(ProductDataSource()))
    }
    private lateinit var binding: FragmentAddProductBinding
    private val REQUEST_IMAGE_ALBUM = 1
    private var bitmap: Bitmap? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddProductBinding.bind(view)
        takeImage()
        binding.ivProductImage.setOnClickListener { takeImage() }
        binding.buttonAddProduct.setOnClickListener {
            setData()
        }
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
        bitmap?.let { bitmap ->
            viewModel.addProduct(bitmap, product)
                .observe(viewLifecycleOwner, {
                    when (it) {
                        is Result.Loading -> {
                            binding.progressBar.show()
                            Toast.makeText(requireContext(), "Subiendo foto", Toast.LENGTH_LONG)
                                .show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_ALBUM && resultCode == Activity.RESULT_OK) {
            binding.ivProductImage.setImageURI(data?.data)
            val imageBitMap = (binding.ivProductImage.drawable as BitmapDrawable).bitmap
            bitmap = imageBitMap
        }
    }
}




