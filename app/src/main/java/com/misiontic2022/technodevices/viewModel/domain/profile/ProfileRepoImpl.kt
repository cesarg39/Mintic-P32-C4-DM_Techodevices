package com.misiontic2022.technodevices.viewModel.domain.profile

import com.misiontic2022.technodevices.model.models.User
import com.misiontic2022.technodevices.model.remote.ProfileDataSource

class ProfileRepoImpl(val dataSource: ProfileDataSource):ProfileRepo {
    override suspend fun getProfileData(): User = dataSource.getProfileData()
    override suspend fun setProfileData(profile: User) {
        dataSource.setProfileData(profile)
    }

}