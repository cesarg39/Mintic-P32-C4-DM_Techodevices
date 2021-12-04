package com.misiontic2022.technodevices.viewModel.presentation.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.model.models.Comment
import com.misiontic2022.technodevices.viewModel.domain.comment.CommentRepo
import kotlinx.coroutines.Dispatchers

class CommentViewModel(private val repo: CommentRepo): ViewModel() {
    fun addComment(description: String, productId: String)= liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.addComment(description, productId)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
    fun getComments(productId: String)= liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.getComments(productId))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}
class CommentViewModelFactory(private val repo: CommentRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentViewModel(repo) as T
    }
}