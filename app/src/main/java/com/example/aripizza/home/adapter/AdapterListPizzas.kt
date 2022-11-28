package com.example.aripizza.home.adapter

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.aripizza.databinding.ItemListPizzasBinding
import com.example.aripizza.model.Pizza

class AdapterListPizzas(var list : MutableList<Pizza>) :
RecyclerView.Adapter<AdapterListPizzas.ViewHolderListPizzas>(){
    lateinit var actionSelect : ((position : Int) -> Unit)
    private var holderList = HashMap<Int,ViewHolderListPizzas> ()
    inner class ViewHolderListPizzas(
        val itemBinding : ItemListPizzasBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item : Pizza, position : Int) {
            itemBinding.imgPizza.setImageResource(item.image)
            itemBinding.imgPizza.setOnClickListener {
                ObjectAnimator.ofPropertyValuesHolder(
                    itemBinding.imgPizza,
                    PropertyValuesHolder.ofFloat(View.SCALE_X,0.9f),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y,0.9f)
                ).apply {
                    duration = 50
                    addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator?) {

                        }

                        override fun onAnimationEnd(p0: Animator?) {
                            ObjectAnimator.ofPropertyValuesHolder(
                                itemBinding.imgPizza,
                                PropertyValuesHolder.ofFloat(View.SCALE_X,1f),
                                PropertyValuesHolder.ofFloat(View.SCALE_Y,1f)
                            ).apply {
                                duration = 50
                                addListener(object : Animator.AnimatorListener {
                                    override fun onAnimationStart(p0: Animator?) {
                                    }

                                    override fun onAnimationEnd(p0: Animator?) {
                                        actionSelect.invoke(position)
                                    }

                                    override fun onAnimationCancel(p0: Animator?) {
                                    }

                                    override fun onAnimationRepeat(p0: Animator?) {
                                    }

                                })

                                start()
                            }
                        }

                        override fun onAnimationCancel(p0: Animator?) {

                        }

                        override fun onAnimationRepeat(p0: Animator?) {

                        }

                    })
                    start()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPizzas {
        val itemBinding = ItemListPizzasBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolderListPizzas(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderListPizzas, position: Int) {
        if(!holderList.containsKey(position)) {
            holderList[position] = holder
        }
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size

    fun getViewByPosition(position : Int) : ViewHolderListPizzas{
        return holderList[position]!!
    }
}