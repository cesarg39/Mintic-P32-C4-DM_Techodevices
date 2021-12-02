package com.misiontic2022.technodevices.viewModel.domain.product

import android.graphics.Bitmap
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.model.models.Product

interface ProductRepo {
    suspend fun  getLatestProduct(): Result<List<Product>>
    suspend fun addProduct(imageBitmap: Bitmap, product: Product)
}