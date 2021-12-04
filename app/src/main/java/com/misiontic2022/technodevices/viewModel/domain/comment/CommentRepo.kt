package com.misiontic2022.technodevices.viewModel.domain.comment

import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.model.models.Comment

interface CommentRepo {
    suspend fun addComment(description: String, productId:String)
    suspend fun getComments(productId: String):Result<List<Comment>>
}