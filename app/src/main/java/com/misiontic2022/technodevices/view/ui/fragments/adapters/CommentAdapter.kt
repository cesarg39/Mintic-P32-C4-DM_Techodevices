package com.misiontic2022.technodevices.view.ui.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.misiontic2022.technodevices.core.BaseViewHolder
import com.misiontic2022.technodevices.databinding.ItemCommentsBinding
import com.misiontic2022.technodevices.model.models.Comment

class CommentAdapter(private val commentList: List<Comment>
) :
RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val commentBinding =
            ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = CommentViewHolder(commentBinding, parent.context)
        return CommentViewHolder(commentBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is CommentViewHolder -> holder.bind(commentList[position])
        }
    }

    override fun getItemCount(): Int = commentList.size
    private inner class CommentViewHolder(
        val binding: ItemCommentsBinding,
        val context: Context
    ) : BaseViewHolder<Comment>(binding.root) {
        override fun bind(item: Comment) {
            binding.ItemCommentsComment.text = item.description
            binding.tvItemCommentsUser.text = item.userName
        }
    }
}
