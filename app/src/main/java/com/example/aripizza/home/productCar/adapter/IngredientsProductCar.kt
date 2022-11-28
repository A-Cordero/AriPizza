package com.example.aripizza.home.productCar.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aripizza.databinding.ItemCarIngredientBinding
import com.example.aripizza.detail.DetailPizzaActivity
import com.example.aripizza.detail.adapter.AdapterIngredients
import com.example.aripizza.model.Ingredient

class IngredientsProductCar (val list: MutableList<Ingredient>) :
    RecyclerView.Adapter<IngredientsProductCar.ViewHolderAdapterIngredients>() {

    inner class ViewHolderAdapterIngredients(
        private val itemBinding: ItemCarIngredientBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: Ingredient, position: Int) {
            itemBinding.apply {
                imgIngredient.setImageResource(item.imageComplete)
                textName.text = item.name
                textPrice.text = "$ "+item.price
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderAdapterIngredients {
        val itemBinding = ItemCarIngredientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolderAdapterIngredients(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderAdapterIngredients, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size


}