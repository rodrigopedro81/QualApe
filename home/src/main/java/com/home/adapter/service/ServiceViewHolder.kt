package com.home.adapter.service

import androidx.recyclerview.widget.RecyclerView
import com.domain.model.Service
import com.home.databinding.ItemProductBinding

class ServiceViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(service: Service) {
        with(binding) {
//            imageViewProduct.loadImage()
            textViewProductName.text = service.title
            textViewProductType.text = service.type.name
            textViewProductValue.text = service.value.toString()
        }
    }
}