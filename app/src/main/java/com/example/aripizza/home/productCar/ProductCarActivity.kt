package com.example.aripizza.home.productCar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.aripizza.databinding.ActivityProductCarBinding
import com.example.aripizza.home.productCar.adapter.ProductCard

class ProductCarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductCarBinding
    var totalClicks = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductCarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
        setListeners()
    }

    private fun setListeners() {

        binding.btnAdd.setOnClickListener {
            completePizza()
        }
        binding.textAmount.setOnClickListener {
            totalClicks += 1
            if (totalClicks >= 5) {
                temporalCredit += 20
                totalClicks = 0
            }
            setView()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun completePizza() {
        if (temporalCredit < getTotal()) {
            Toast.makeText(
                this,
                "No cuenta con los créditos necesarios para completar la compra.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            DialogComplete(this).show()
            temporalCredit -= getTotal()
            temporalCar = ProductCar()
            setView()
        }
    }

    private fun setView() {
        binding.textAmount.text = "$ " + String.format("%.2f", getTotal())
        binding.textCredits.text = "$ " + String.format("%.2f", temporalCredit)
        setAdapter()
    }

    private fun setAdapter() {
        if (temporalCar.listPizzaCar.isNotEmpty()) {
            binding.imgEmpty.visibility = View.INVISIBLE
        } else {
            binding.imgEmpty.visibility = View.VISIBLE
        }
        binding.rvCar.adapter = ProductCard(temporalCar.listPizzaCar)
    }

    fun getTotal(): Double {
        var amount = 0.0
        temporalCar.listPizzaCar.forEach { pizza ->
            amount += pizza.price
            pizza.listIngredients.forEach { ingredient ->
                amount += ingredient.price
            }
        }
        return amount
    }
}