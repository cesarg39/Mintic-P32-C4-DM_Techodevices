package com.misiontic2022.technodevices.model.remote

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.misiontic2022.technodevices.model.models.Product
import kotlinx.coroutines.tasks.await
import com.misiontic2022.technodevices.core.Result
import kotlinx.coroutines.currentCoroutineContext
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.coroutines.coroutineContext

class ProductDataSource {
    suspend fun getLatestProduct(myProducts: Boolean): Result<List<Product>> {
        val productList = mutableListOf<Product>()
        var querySnapshot: QuerySnapshot? = null
        if (myProducts) {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                querySnapshot = FirebaseFirestore.getInstance().collection("products")
                    .whereEqualTo("uid", user.uid).get().await()
            }

        } else {
            querySnapshot = FirebaseFirestore.getInstance().collection("products").get().await()
        }
        for (product in querySnapshot!!) {
            product.toObject(Product::class.java).let {
                productList.add(it)
            }
        }
        return Result.Success(productList)
    }

    suspend fun addProduct(imageBitmap: Bitmap, product: Product) {
        val user = FirebaseAuth.getInstance().currentUser
        val randomName = UUID.randomUUID().toString()
        val imageRef =
            FirebaseStorage.getInstance().reference.child("${user?.uid}/products/$randomName")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val downloadUrl =
            imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        user?.let {
            FirebaseFirestore.getInstance().collection("products").add(
                Product(
                    photo = downloadUrl,
                    title = product.title,
                    price = product.price,
                    description = product.description,
                    uid = it.uid
                )
            )
        }
    }

    suspend fun deleteProduct(product: Product) {
        val user = FirebaseAuth.getInstance().currentUser
        Log.d("qweqr", "aaaaaaaaa")
        val snapshot = FirebaseFirestore.getInstance().collection("products")
            .whereEqualTo("title", product.title).whereEqualTo("uid", product.uid).whereEqualTo("photo", product.photo).get().await()
        for (product in snapshot!!) {
            product.reference.delete()
        }
    }
}
