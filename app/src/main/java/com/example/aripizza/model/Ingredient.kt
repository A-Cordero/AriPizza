package com.example.aripizza.model

import android.widget.ImageView

class Ingredient(
    var name : String,
    var imageComplete : Int,
    var imageMini : Int,
    var price : Double,
    var selected : Boolean = false,
    var viewsInPizza : MutableList<ImageView> = mutableListOf()
)