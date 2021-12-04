package com.misiontic2022.technodevices.model.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.misiontic2022.technodevices.model.models.Comment
import com.misiontic2022.technodevices.core.Result
import kotlinx.coroutines.tasks.await


class CommentDataSource {
    suspend fun addComment(description: String, productId: String) {
        val comment = FirebaseFirestore.getInstance().collection("comments").document()
        val id = comment.id
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val userName =FirebaseFirestore.getInstance().collection("users").document(it.uid).get().await()
                comment.set(
                Comment(
                    id = id,
                    userName = userName.get("name").toString(),
                    description = description,
                    productId = productId
                )
            )
        }

    }

    suspend fun getComments(productId: String): Result<List<Comment>> {
        val commentList = mutableListOf<Comment>()
        var querySnapshot: QuerySnapshot? = null
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            querySnapshot = FirebaseFirestore.getInstance().collection("comments").whereEqualTo("productId",productId).get().await()
        }
        for (product in querySnapshot!!) {
            product.toObject(Comment::class.java).let {
                commentList.add(it)
            }
        }
        return Result.Success(commentList)

    }
}