package com.misiontic2022.technodevices.viewModel.domain.product

import android.graphics.Bitmap
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.model.models.Product
import com.misiontic2022.technodevices.model.remote.ProductDataSource

class ProductRepoImpl(private val dataSource: ProductDataSource):ProductRepo {
    override suspend fun getLatestProduct(myProducts: Boolean): Result<List<Product>> = dataSource.getLatestProduct(myProducts)
    override suspend fun addProduct(imageBitmap: Bitmap, product: Product) = dataSource.addProduct(imageBitmap,product)
}