package com.misiontic2022.technodevices.viewModel.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.viewModel.domain.auth.AuthRepo

class AuthViewModel(private val repo: AuthRepo) : ViewModel() {
    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.signIn(email, password)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun signUp(email: String, password: String, name: String, address: String, phone: String) =
        liveData(Dispatchers.IO) {
            emit(Result.Loading())
            try {
                emit(Result.Success(repo.signUp(email, password, name, address, phone)))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    fun signOut() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.signOut()))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}


class AuthViewModelFactory(private val repo: AuthRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }
}