package com.misiontic2022.technodevices.view.ui.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.misiontic2022.technodevices.core.BaseViewHolder
import com.misiontic2022.technodevices.databinding.ItemProductBinding
import com.misiontic2022.technodevices.model.models.Product

class ProductAdapter(
    private val productList: List<Product>,
    private val itemClickListener: OnProductClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ProductViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener{
            val position = holder.bindingAdapterPosition.takeIf { it !=  DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onProductClick(productList[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ProductViewHolder -> holder.bind(productList[position])
        }
    }

    override fun getItemCount(): Int = productList.size
    private inner class ProductViewHolder(
        val binding: ItemProductBinding,
        val context: Context
    ) : BaseViewHolder<Product>(binding.root) {
        override fun bind(item: Product) {
            Glide.with(context).load(item.photo).fitCenter().into(binding.imProduct)
            binding.txtName.text = item.title
            binding.txtPrice.text = item.price
            binding.cardViewProduct.setOnClickListener{
                itemClickListener.onProductClick(productList[absoluteAdapterPosition])
            }
        }
    }
}
