package com.home.adapter.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.domain.model.Service
import com.home.databinding.ItemProductBinding

class ServicesAdapter(
    items:List<Service>
): RecyclerView.Adapter<ServiceViewHolder>() {

    private val items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }
}
