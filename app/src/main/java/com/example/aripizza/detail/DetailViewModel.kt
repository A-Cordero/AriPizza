package com.example.aripizza.detail

import androidx.lifecycle.ViewModel
import com.example.aripizza.R
import com.example.aripizza.model.Ingredient
import com.example.aripizza.model.Pizza

class DetailViewModel : ViewModel() {

    var _pizza : Pizza ?= null
    val pizza get() = _pizza!!

    var listIngredientsSelected : MutableList<Ingredient> = mutableListOf()


    fun getTotalAmount() : Double {
        var amount = 0.0
        listIngredientsSelected.forEach {
            amount += it.price
        }

        amount += pizza.price
        return amount
    }
    var listIngredients : MutableList<Ingredient> =
        mutableListOf(
            Ingredient(
                name = "cebolla",
                imageComplete = R.drawable.onion,
                imageMini = R.drawable.onion,
                price = 2.0
            ),

            Ingredient(
                name = "champiñon",
                imageComplete = R.drawable.mushroom_unit,
                imageMini = R.drawable.mushroom,
                price = 2.0
            ),


            Ingredient(
                name = "guisante",
                imageComplete = R.drawable.pea,
                imageMini = R.drawable.pea_unit,
                price = 2.0
            ),

            Ingredient(
                name = "pepinillo",
                imageComplete = R.drawable.pickle,
                imageMini = R.drawable.pickle_unit,
                price = 2.0
            ),

            Ingredient(
                name = "aceitunas",
                imageComplete = R.drawable.olive,
                imageMini = R.drawable.olive_unit,
                price = 2.0
            ),

            Ingredient(
                name = "chili",
                imageComplete = R.drawable.chili,
                imageMini = R.drawable.chili_unit,
                price = 2.0
            ),
        )
}