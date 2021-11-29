package com.misiontic2022.technodevices.model.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.misiontic2022.technodevices.model.models.User
import kotlinx.coroutines.tasks.await

class AuthDataSource {
    suspend fun signIn(email: String, password: String): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }
    suspend fun signUp(email: String, password: String, name: String, address:String, phone:String): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        authResult.user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .set(User(name, email, address, phone)).await()
        }
        return authResult.user
    }

    suspend fun signOut(){
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            FirebaseAuth.getInstance().signOut()
        }
    }
}