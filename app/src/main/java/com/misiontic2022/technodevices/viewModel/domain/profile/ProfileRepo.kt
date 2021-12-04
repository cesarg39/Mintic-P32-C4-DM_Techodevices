package com.misiontic2022.technodevices.viewModel.domain.profile

import com.misiontic2022.technodevices.model.models.User

interface ProfileRepo {
    suspend fun getProfileData():User
    suspend fun getProfileSellerData(uid: String?):User
    suspend fun setProfileData(profile:User)
}