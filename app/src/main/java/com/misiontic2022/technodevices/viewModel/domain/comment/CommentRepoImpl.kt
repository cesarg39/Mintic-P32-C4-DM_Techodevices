package com.misiontic2022.technodevices.viewModel.domain.comment


import com.misiontic2022.technodevices.core.Result
import com.misiontic2022.technodevices.model.remote.CommentDataSource
import com.misiontic2022.technodevices.model.models.Comment

class CommentRepoImpl(val dataSource: CommentDataSource):CommentRepo {
    override suspend fun addComment(description: String, productId: String) = dataSource.addComment(description, productId)
    override suspend fun getComments(productId: String): Result<List<Comment>> = dataSource.getComments(productId)
}