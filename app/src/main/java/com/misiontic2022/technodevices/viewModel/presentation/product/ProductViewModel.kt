package com.misiontic2022.technodevices.viewModel.presentation.product

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.misiontic2022.technodevices.viewModel.domain.product.ProductRepo
import kotlinx.coroutines.Dispatchers
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.model.models.Product

class ProductViewModel(private val repo: ProductRepo) : ViewModel() {
    private lateinit var productList: Unit
    fun getLatestProduct(myProducts: Boolean = false) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            productList = emit(repo.getLatestProduct(myProducts))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun addProduct(imageBitmap: Bitmap, product: Product) = liveData(Dispatchers.Main) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.addProduct(imageBitmap, product)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}


class ProductViewModelFactory(private val repo: ProductRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(repo) as T
    }
}