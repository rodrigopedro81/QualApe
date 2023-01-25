package com.home.adapter.product

import androidx.recyclerview.widget.RecyclerView
import com.domain.model.Product
import com.domain.model.Service
import com.home.databinding.ItemProductBinding

class ProductViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        with(binding) {
//            imageViewProduct.loadImage()
            textViewProductName.text = product.title
            textViewProductType.text = product.type.name
            textViewProductValue.text = product.value.toString()
        }
    }
}