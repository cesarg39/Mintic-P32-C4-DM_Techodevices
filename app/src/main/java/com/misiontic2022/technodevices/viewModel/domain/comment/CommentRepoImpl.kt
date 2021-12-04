package com.misiontic2022.technodevices.viewModel.domain.comment

import com.misiontic2022.technodevices.model.remote.CommentDataSource

class CommentRepoImpl(val dataSource: CommentDataSource):CommentRepo {
    override suspend fun addComment(description: String, productId: String) = dataSource.addComment(description, productId)
}