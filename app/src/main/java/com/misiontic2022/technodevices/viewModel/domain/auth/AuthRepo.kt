package com.misiontic2022.technodevices.viewModel.domain.auth

import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    suspend fun signIn(email: String,password: String): FirebaseUser?
    suspend fun signUp(email: String, password: String, name: String, address:String, phone:String): FirebaseUser?
}