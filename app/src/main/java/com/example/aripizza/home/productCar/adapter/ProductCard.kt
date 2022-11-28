package com.example.aripizza.home.productCar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aripizza.databinding.ItemCarBinding
import com.example.aripizza.databinding.ItemCarIngredientBinding
import com.example.aripizza.home.productCar.temporalCar
import com.example.aripizza.model.Ingredient
import com.example.aripizza.model.Pizza

class ProductCard (val list: MutableList<Pizza>) :
    RecyclerView.Adapter<ProductCard.ViewHolderCar>() {

    inner class ViewHolderCar(
        private val itemBinding: ItemCarBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: Pizza, position: Int) {
            itemBinding.apply {
                textName.text = item.name
                imgPizza.setImageResource(item.image)
                textPricePizza.text = item.price.toString()
                itemBinding.rvIngredients.adapter = IngredientsProductCar(item.listIngredients)

                textTotal.text = "$ "+getTotal(item).toString()
            }
        }

        fun getTotal(pizza : Pizza) : Double{
            var amount = 0.0

            amount+=pizza.price
            pizza.listIngredients.forEach { ingredient ->
                amount+=ingredient.price
            }
            return amount
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderCar {
        val itemBinding = ItemCarBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolderCar(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderCar, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size


}