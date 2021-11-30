package com.misiontic2022.technodevices.model.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.misiontic2022.technodevices.model.models.User
import kotlinx.coroutines.tasks.await

class ProfileDataSource {
    suspend fun getProfileData(): User {
        val user = FirebaseAuth.getInstance().currentUser
        val profile: User = User()
        user?.let {
            val querySnapShot =
                FirebaseFirestore.getInstance().collection("users").document(user.uid).get().await()
                    ?.let {
                        if (it.exists()) {
                            profile.name = it.getString("name")!!
                            profile.address = it.getString("address")!!
                            profile.email = it.getString("email")!!
                            profile.phone = it.getString("phone")!!
                        }
                    }
        }
        return profile
    }
suspend fun setProfileData(profile:User){
    val user = FirebaseAuth.getInstance().currentUser
    user?.let {
        FirebaseFirestore.getInstance().collection("users").document(user.uid)
            .set(profile).await()
    }
}

}