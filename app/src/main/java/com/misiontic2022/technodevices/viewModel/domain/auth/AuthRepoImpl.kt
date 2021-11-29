package com.misiontic2022.technodevices.viewModel.domain.auth

import com.google.firebase.auth.FirebaseUser
import com.misiontic2022.technodevices.model.remote.AuthDataSource

class AuthRepoImpl(val dataSource: AuthDataSource) : AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? =
        dataSource.signIn(email, password)


    override suspend fun signUp(
        email: String,
        password: String,
        name: String,
        address: String,
        phone: String
    ): FirebaseUser? = dataSource.signUp(email, password, name, address, phone)

    override suspend fun signOut() {
        dataSource.signOut()
    }


}