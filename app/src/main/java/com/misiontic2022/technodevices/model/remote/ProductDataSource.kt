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
import com.misiontic2022.technodevices.model.models.User
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
            val productDb = FirebaseFirestore.getInstance().collection("products").document()
            val documentId = productDb.id
            productDb.set(Product(
                id = documentId,
                photo = downloadUrl,
                title = product.title,
                price = product.price,
                description = product.description,
                uid = it.uid,
            ))

        }
    }

    suspend fun deleteProduct(product: Product) {
        FirebaseFirestore.getInstance().collection("products").document(product.id).delete()
    }

    suspend fun getProductData(): Product {
        val user = FirebaseAuth.getInstance().currentUser
        val product: Product = Product()
        user?.let {
            val snapshot = FirebaseFirestore.getInstance().collection("products")
                .whereEqualTo("title", "xiaomi 2").get().await()
            for (producto in snapshot!!) {
                product.photo = producto.getString("photo")!!
                product.title = producto.getString("title")!!
                product.price = producto.getString("price")!!
                product.description = producto.getString("description")!!
            }
        }
        return product
    }
}
