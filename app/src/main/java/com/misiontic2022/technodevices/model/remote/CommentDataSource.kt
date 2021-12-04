package com.misiontic2022.technodevices.model.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.misiontic2022.technodevices.model.models.Comment


class CommentDataSource {
    suspend fun addComment(description: String, productId:String){
        val comment = FirebaseFirestore.getInstance().collection("comments").document()
        val id = comment.id
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            comment.set(
                Comment(id =id, userId = user.uid, description = description, productId = productId)
            )
        }

    }
}