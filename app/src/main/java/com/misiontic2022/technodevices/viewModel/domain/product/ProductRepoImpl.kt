package com.misiontic2022.technodevices.viewModel.domain.product

import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.model.models.Product
import com.misiontic2022.technodevices.model.remote.ProductDataSource

class ProductRepoImpl(private val dataSource: ProductDataSource):ProductRepo {
    override suspend fun getLatestProduct(): Result<List<Product>> = dataSource.getLatestProduct()
}