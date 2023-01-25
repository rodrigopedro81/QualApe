package com.home.adapter.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.domain.model.Food
import com.home.databinding.ItemProductBinding

class FoodsAdapter(
    items:List<Food>
): RecyclerView.Adapter<FoodViewHolder>() {

    private val items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }
}
