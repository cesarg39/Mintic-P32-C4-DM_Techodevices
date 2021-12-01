package com.misiontic2022.technodevices.viewModel.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.misiontic2022.technodevices.viewModel.domain.product.ProductRepo
import kotlinx.coroutines.Dispatchers
import com.misiontic2022.technodevices.core.Result

class ProductViewModel (private val repo: ProductRepo): ViewModel(){
    private lateinit var productList:Unit
    fun getLatestProduct() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            productList = emit(repo.getLatestProduct())
        }catch(e:Exception){
            emit(Result.Failure(e))
        }
    }
}




class ProductViewModelFactory(private val repo :ProductRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(repo) as T
    }
}