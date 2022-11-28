package com.example.aripizza.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aripizza.R
import com.example.aripizza.model.Pizza
import kotlinx.coroutines.selects.select

class MainViewModel : ViewModel() {


    var listPizzas = mutableListOf<Pizza>(
        Pizza(
            name = "Pizza de Peperoni",
            price = 12.0,
            rating = 4.3f,
            image = R.drawable.pizza0,
            selected = true
        ),
        Pizza(
            name = "Pizza Margarita",
            price = 11.5,
            rating = 3.3f,
            image = R.drawable.pizza1
        ),
        Pizza(
            name = "Pizza Napolitana",
            price = 14.0,
            rating = 4.7f,
            image = R.drawable.pizza2
        ),

        Pizza(
            name = "Pizza de champiñones",
            price = 13.0,
            rating = 4.9f,
            image = R.drawable.pizza3
        ),

        Pizza(
            name = "Pizza de peperoni & mozzarella",
            price = 17.0,
            rating = 4.0f,
            image = R.drawable.pizza4
        ),

        Pizza(
            name = "Pizza con Berenjena",
            price = 12.6,
            rating = 3.7f,
            image = R.drawable.pizza5
        ),
        Pizza(
            name = "Pizza cuatro estaciones",
            price = 15.0,
            rating = 5.0f,
            image = R.drawable.pizza6
        ),

        Pizza(
            name = "Pizza con pollo y champiñones",
            price = 16.0,
            rating = 4.7f,
            image = R.drawable.pizza8
        ),

        Pizza(
            name = "Pizza Margarita doble queso",
            price = 13.4,
            rating = 4.8f,
            image = R.drawable.pizza9
        ),

        Pizza(
            name = "Pizza Vegetariana",
            price = 15.0,
            rating = 4.1f,
            image = R.drawable.pizza10
        ),
    )
}