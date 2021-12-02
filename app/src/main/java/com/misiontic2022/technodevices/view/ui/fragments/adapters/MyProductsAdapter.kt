package com.misiontic2022.technodevices.view.ui.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.misiontic2022.technodevices.core.BaseViewHolder
import com.misiontic2022.technodevices.core.hide
import com.misiontic2022.technodevices.core.show
import com.misiontic2022.technodevices.databinding.MyProductDetailBinding
import com.misiontic2022.technodevices.model.models.Product
import com.misiontic2022.technodevices.view.ui.fragments.CreateProductFragment

class MyProductsAdapter(
    private val productList: List<Product>,
    private val itemClickListener: CreateProductFragment
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface OnProductClickListener {
        fun onProductClick(product: Product, action :Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            MyProductDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        val binding: MyProductDetailBinding,
        val context: Context
    ) : BaseViewHolder<Product>(binding.root) {
        override fun bind(item: Product) {
            Glide.with(context).load(item.photo).fitCenter().into(binding.imProduct)
            binding.txtName.text = item.title
            binding.txtPrice.text = item.price
            binding.cardViewProduct.setOnLongClickListener {
                binding.infoLayout.hide()
                binding.buttons.show()
                true
            }
            binding.buttonDelete.setOnClickListener {
                itemClickListener.onProductClick(productList[absoluteAdapterPosition],1)
            }
        }
    }
}