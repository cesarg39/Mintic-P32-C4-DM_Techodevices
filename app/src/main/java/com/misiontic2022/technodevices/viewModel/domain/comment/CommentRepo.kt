package com.misiontic2022.technodevices.viewModel.domain.comment

interface CommentRepo {
    suspend fun addComment(description: String, productId:String)
}