package com.example.aripizza.detail.adapter

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aripizza.databinding.ItemIngredientInPizzaBinding
import com.example.aripizza.model.Ingredient

class AdapterIngredientsInPizza(private val list: MutableList<String>) :
    RecyclerView.Adapter<AdapterIngredientsInPizza.ViewHolderAdapterIngredientsInPizza>() {

    inner class ViewHolderAdapterIngredientsInPizza(
        private val itemBinding: ItemIngredientInPizzaBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: String, position: Int) {
            itemBinding.apply {

                ObjectAnimator.ofPropertyValuesHolder(
                    itemIngredient,
                    PropertyValuesHolder.ofFloat("translationY",100f),
                    PropertyValuesHolder.ofFloat("translationX",100f)
                ).apply {
                    duration = 2000
                    start()
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderAdapterIngredientsInPizza {
        val itemBinding = ItemIngredientInPizzaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolderAdapterIngredientsInPizza(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderAdapterIngredientsInPizza, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size


}