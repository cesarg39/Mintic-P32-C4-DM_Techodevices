package com.misiontic2022.technodevices.model.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.misiontic2022.technodevices.model.models.Product
import kotlinx.coroutines.tasks.await
import com.misiontic2022.technodevices.core.Result

class ProductDataSource {
    suspend fun getLatestProduct():Result<List<Product>>{
        val productList = mutableListOf<Product>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("products").get().await()
        for (product in querySnapshot){
            product.toObject(Product::class.java).let {
                productList.add(it)
            }
        }
        return Result.Success(productList)
    }
}