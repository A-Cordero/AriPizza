package com.example.aripizza.detail.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aripizza.detail.DetailPizzaActivity
import com.example.aripizza.databinding.ItemIngredientBinding
import com.example.aripizza.model.Ingredient

class AdapterIngredients(val list: MutableList<Ingredient>) :
    RecyclerView.Adapter<AdapterIngredients.ViewHolderAdapterIngredients>() {

    lateinit var adapterDragAction : ((item : String, view: View) -> Unit)
    inner class ViewHolderAdapterIngredients(
        private val itemBinding: ItemIngredientBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: Ingredient, position: Int) {
            itemBinding.apply {
                imgIngredient.setImageResource(item.imageComplete)

                if(item.selected) {
                    imgIngredient.alpha = 0.2f
                    imgIngredient.isEnabled = false
                } else {
                    imgIngredient.alpha = 1f
                    imgIngredient.isEnabled = true
                }


                imgIngredient.setOnTouchListener { view, motionEvent ->

                        val itemClip = ClipData.Item(item.name)
                        val dragData = ClipData(
                            item.name,
                            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                            itemClip
                        )
                        val myShadow = DetailPizzaActivity.MyDragShadowBuilder(imgIngredient)
                        imgIngredient.startDragAndDrop(dragData, myShadow, null, 0)
                        true

                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderAdapterIngredients {
        val itemBinding = ItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolderAdapterIngredients(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderAdapterIngredients, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size


}