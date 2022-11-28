package com.example.aripizza.home.productCar

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.example.aripizza.databinding.DialogCompleteBinding

class DialogComplete (context: Context): Dialog(context){
    private var myContext: Context =context
    private lateinit var binding: DialogCompleteBinding
    init {
        initDialog()
    }
    private fun initDialog() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setCancelable(false)
        binding = DialogCompleteBinding.inflate(LayoutInflater.from(myContext))
        this.setContentView(binding.root)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        binding.btnAccept.setOnClickListener {
            this.dismiss()
        }
    }

    fun setProgress(state : Boolean) {
        when(state) {
            true -> this.show()
            false -> this.dismiss()
        }
    }
}