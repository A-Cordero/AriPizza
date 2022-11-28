package com.example.aripizza.model

class Pizza(
    var name: String,
    var price: Double,
    var rating: Float,
    var image: Int,
    var selected : Boolean = false,
    var listIngredients: MutableList<Ingredient> = mutableListOf(),
) : java.io.Serializable