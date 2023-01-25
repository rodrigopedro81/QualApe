package com.home.adapter.food

import androidx.recyclerview.widget.RecyclerView
import com.domain.model.Food
import com.domain.model.Service
import com.home.databinding.ItemProductBinding

class FoodViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(food: Food) {
        with(binding) {
//            imageViewProduct.loadImage()
            textViewProductName.text = food.title
            textViewProductType.text = food.type.name
            textViewProductValue.text = food.value.toString()
        }
    }
}