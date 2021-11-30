package com.misiontic2022.technodevices.viewModel.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.model.models.User
import com.misiontic2022.technodevices.viewModel.domain.profile.ProfileRepo
import kotlinx.coroutines.Dispatchers

class ProfileViewModel (private val repo: ProfileRepo) : ViewModel() {
    fun getProfileData() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.getProfileData()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun setProfileData(profile: User) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.setProfileData(profile)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

}
class ProfileViewModelFactory(private val repo: ProfileRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repo) as T
    }
}