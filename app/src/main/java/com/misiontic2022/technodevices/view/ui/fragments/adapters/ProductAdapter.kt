package com.misiontic2022.technodevices.view.ui.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.misiontic2022.technodevices.core.BaseViewHolder
import com.misiontic2022.technodevices.databinding.ProductDetailBinding
import com.misiontic2022.technodevices.model.models.Product

class ProductAdapter(
    private val productList: List<Product>,
    private val itemClickListener: OnProductClickListener
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ProductDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ProductViewHolder(itemBinding, parent.context)
        return ProductViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is ProductViewHolder -> holder.bind(productList[position])
        }
    }

    override fun getItemCount(): Int = productList.size
    private inner class ProductViewHolder(
        val binding: ProductDetailBinding,
        val context: Context
    ) : BaseViewHolder<Product>(binding.root) {
        override fun bind(item: Product) {
            Glide.with(context).load(item.photo).centerCrop().into(binding.imProduct)
            binding.txtName.text = item.title
            binding.txtPrice.text = item.price
        }
    }
}
