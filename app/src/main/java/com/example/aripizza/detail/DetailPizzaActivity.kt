package com.example.aripizza.detail

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import com.example.aripizza.R
import com.example.aripizza.detail.adapter.AdapterIngredients
import com.example.aripizza.databinding.ActivityDetailPizzaBinding
import com.example.aripizza.home.productCar.ProductCarActivity
import com.example.aripizza.home.productCar.temporalCar
import com.example.aripizza.model.Pizza
import kotlin.math.*

class DetailPizzaActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailPizzaBinding

    var inside = false

    private val adapterIngredients: AdapterIngredients by lazy {
        AdapterIngredients(viewModel.listIngredients)
    }

    private val viewModel : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPizzaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPizza()
        setAdapter()
        setDrag()
        setListeners()
        updatePizzaCar()
    }

    private fun getPizza() {
        viewModel._pizza = intent.getSerializableExtra("pizza") as Pizza
        setViewPizza()
    }

    private fun setViewPizza() {
        binding.apply {
            imgPizza.setImageResource(viewModel.pizza.image)
            imgPizzaForAnimation.setImageResource(viewModel.pizza.image)
            textNameToolbar.text = viewModel.pizza.name
            textNameToolbarBack.text = viewModel.pizza.name
            textRemaining.text = (4 - viewModel.listIngredientsSelected.size).toInt().toString()
        }
        setAmount(viewModel.getTotalAmount())
    }

    private fun setListeners() {
        binding.containerCar.setOnClickListener {
            startActivity(Intent(this, ProductCarActivity::class.java))
        }

        binding.btnAdd.setOnClickListener {
            ObjectAnimator.ofPropertyValuesHolder(
                binding.containerBtnAdd,
                PropertyValuesHolder.ofFloat(View.SCALE_X,0.6f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y,0.6f)
            ).apply {
                duration = 100
                addListener(object : AnimatorListener{
                    override fun onAnimationStart(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        ObjectAnimator.ofPropertyValuesHolder(
                            binding.containerBtnAdd,
                            PropertyValuesHolder.ofFloat(View.SCALE_X,1f),
                            PropertyValuesHolder.ofFloat(View.SCALE_Y,1f)
                        ).apply {
                            duration = 100
                            addListener(object : AnimatorListener {
                                override fun onAnimationStart(p0: Animator?) {
                                }

                                override fun onAnimationEnd(p0: Animator?) {
                                    addPizzaToCar()
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
        binding.itemPizza.setOnClickListener {

            ObjectAnimator.ofPropertyValuesHolder(
                binding.itemPizza,
                PropertyValuesHolder.ofFloat(View.SCALE_X,0.9f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y,0.9f)
            ).apply {
                duration = 100
                addListener(object : AnimatorListener{
                    override fun onAnimationStart(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        ObjectAnimator.ofPropertyValuesHolder(
                            binding.itemPizza,
                            PropertyValuesHolder.ofFloat(View.SCALE_X,1f),
                            PropertyValuesHolder.ofFloat(View.SCALE_Y,1f)
                        ).apply {
                            duration = 100
                            addListener(object : AnimatorListener {
                                override fun onAnimationStart(p0: Animator?) {
                                }

                                override fun onAnimationEnd(p0: Animator?) {
                                    removeViewsFromPizza()
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
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setDrag() {
        binding.itemPizza.setOnDragListener(dragListener)
    }

    private fun clearIngredients() {
        if(viewModel.listIngredientsSelected.isNotEmpty()){
            viewModel.listIngredients.first { it.name == viewModel.listIngredientsSelected.last().name }.selected = false
            viewModel.listIngredientsSelected.last().viewsInPizza.forEach {
                binding.containerIngredientsInPizza.removeView(it)
            }
            viewModel.listIngredientsSelected.removeIf { it.name == viewModel.listIngredientsSelected.last().name }
            clearIngredients()
        } else {
            adapterIngredients.notifyDataSetChanged()
            setViewPizza()
        }
    }

    private fun removeViewsFromPizza() {
        if(viewModel.listIngredientsSelected.isNotEmpty()){
            viewModel.listIngredients.first { it.name == viewModel.listIngredientsSelected.last().name }.selected = false
            adapterIngredients.notifyDataSetChanged()
            viewModel.listIngredientsSelected.last().viewsInPizza.forEach {
                binding.containerIngredientsInPizza.removeView(it)
            }
            viewModel.listIngredientsSelected.removeIf { it.name == viewModel.listIngredientsSelected.last().name }
            setViewPizza()
        }
    }

    private fun setAdapter() {

        binding.rvIngredients.adapter = adapterIngredients
        adapterIngredients.adapterDragAction = { item, v ->
            val itemClip = ClipData.Item(v.tag as? CharSequence)
            val dragData = ClipData(
                v.tag as CharSequence,
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                itemClip
            )
            val myShadow = MyDragShadowBuilder(v)
            v.startDragAndDrop(dragData, myShadow, null, 0)
        }
    }


    private var ingredientDrag : CharSequence = ""
    private val dragListener = View.OnDragListener { view, dragEvent ->
        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED -> {

                //binding.textView.text = "Comenzamos con el item " + item
                ingredientDrag = dragEvent.clipDescription.label
                inside = false
                true
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                scalePizzaViewUp()
               // binding.textView.text = "Entro a la pizza"
                inside = true
                true
            }

            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                scalePizzaViewDown()
                //binding.textView.text = "Hemos Salimos"
                inside = false
                true
            }

            DragEvent.ACTION_DROP -> {
                scalePizzaViewDown()
               // binding.textView.text = "Soltamos Soltamos"

                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                //binding.textView.text = "Termino el arrastre"

                if(inside) {
                    if(viewModel.listIngredientsSelected.size < 4){
                        addIngredient(ingredientDrag)
                    }
                }

                true
            }
            else -> {
                true
            }
        }
    }

    class MyDragShadowBuilder(val v: View) : View.DragShadowBuilder(v) {
        override fun onProvideShadowMetrics(outShadowSize: Point, outShadowTouchPoint: Point) {
            outShadowSize.set(view.width, view.height)
            outShadowTouchPoint.set(view.width / 2, view.height / 2)
        }

        override fun onDrawShadow(canvas: Canvas?) {
            v.draw(canvas)
        }
    }

    fun scalePizzaViewUp() {
        ObjectAnimator.ofPropertyValuesHolder(
            binding.itemPizza,
            PropertyValuesHolder.ofFloat("scaleX", 1.25f),
            PropertyValuesHolder.ofFloat("scaleY", 1.25f)
        ).apply {
            duration = 400
            start()
        }
    }

    fun scalePizzaViewDown() {
        ObjectAnimator.ofPropertyValuesHolder(
            binding.itemPizza,
            PropertyValuesHolder.ofFloat("scaleX", 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 1.0f)
        ).apply {
            duration = 400
            start()
        }
    }


    fun addIngredient(label: CharSequence) {
        var ingredient = viewModel.listIngredients.first { it.name == label }
       for (position in 0..4) {
           var imageView = ImageView(this).apply {
               layoutParams = LinearLayout.LayoutParams(100, 100)
               setImageResource(ingredient.imageMini)
               setBackgroundColor(ContextCompat.getColor(this@DetailPizzaActivity,
                   R.color.transparent
               ))
           }
           ingredient.viewsInPizza.add(imageView)

           binding.containerIngredientsInPizza.addView(imageView)
           var heightPizza = binding.imgPizza.measuredHeight
           var widthPizza = binding.imgPizza.measuredWidth
           var centerX = binding.itemPizza.x + widthPizza/2
           var centerY = binding.itemPizza.y + heightPizza/2
           var pizzaRadius  = binding.imgPizza.measuredHeight/2f  -150

           var ang = (0..100).random()/100f*2*Math.PI
           var hyp = sqrt((0..100).random()/100f)*pizzaRadius
           var adj = cos(ang)*hyp
           var opp = sin(ang)*hyp
           var toX = centerX + adj-25
           var toY = centerY + opp-25
           var fromX = if(toX < centerX ) (-1)*toX + centerX else toX + centerX
           var fromY = if(toY < centerY) (-1)*toY + centerY else toY + centerY

           ObjectAnimator.ofPropertyValuesHolder(
               imageView,
               PropertyValuesHolder.ofFloat(View.TRANSLATION_X,fromX.toFloat(),toX.toFloat()),
               PropertyValuesHolder.ofFloat(View.TRANSLATION_Y,fromY.toFloat(),toY.toFloat()),
               PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
           ).apply {
               duration = 500
               interpolator = OvershootInterpolator(0.6f)
               start()
           }
       }
        ingredient.selected = true
        viewModel.listIngredientsSelected.add(ingredient)
        viewModel.listIngredients.first { it.name == label }.selected = true
        adapterIngredients.notifyDataSetChanged()
        setViewPizza()
    }

    fun setAmount(newAmount : Double) {
        var distanceY = 45f
         ObjectAnimator.ofPropertyValuesHolder(
            binding.containerAmountBack,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0F, - distanceY),
            PropertyValuesHolder.ofFloat(View.ALPHA,1f, 0f)
        ).apply {
            duration = 200
            addListener(object : AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.containerAmountBack.alpha = 0f
                    binding.textTotalBack.text = newAmount.toString()
                    ObjectAnimator.ofFloat(binding.containerAmountBack,View.TRANSLATION_Y,0f,+ distanceY).apply {
                        duration = 0
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

        ObjectAnimator.ofPropertyValuesHolder(
            binding.containerAmount,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y,distanceY,0f),
            PropertyValuesHolder.ofFloat(View.ALPHA,0f, 1f)
        ).apply {
            duration = 200
            addListener(object : AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    binding.textAmount.text = newAmount.toString()

                }

                override fun onAnimationEnd(p0: Animator?) {
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            })
            start()
        }
    }

    private fun updatePizzaCar() {
        ObjectAnimator.ofPropertyValuesHolder(
            binding.containerCar,
            PropertyValuesHolder.ofFloat(View.SCALE_X,0.6f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y,0.6f)
        ).apply {
            duration = 100
            addListener(object : AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    ObjectAnimator.ofPropertyValuesHolder(
                        binding.containerCar,
                        PropertyValuesHolder.ofFloat(View.SCALE_X,1f),
                        PropertyValuesHolder.ofFloat(View.SCALE_Y,1f)
                    ).apply {
                        duration = 100
                        addListener(object : AnimatorListener {
                            override fun onAnimationStart(p0: Animator?) {
                                if(temporalCar.listPizzaCar.isEmpty()) {
                                    binding.textQty.text = temporalCar.listPizzaCar.size.toString()
                                    binding.containerQty.visibility = View.INVISIBLE
                                } else {
                                    binding.textQty.text = temporalCar.listPizzaCar.size.toString()
                                    binding.containerQty.visibility = View.VISIBLE
                                }
                            }

                            override fun onAnimationEnd(p0: Animator?) {

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

    private fun addPizzaToCar() {
        var fromX = binding.containerBox.x + binding.containerBox.measuredWidth/2
        var fromY = binding.containerBox.y + binding.containerBox.measuredHeight/2
        var toX = binding.containerCar.x + binding.containerCar.measuredWidth/2
        var toY = binding.containerCar.y + binding.containerCar.measuredHeight/2

        binding.containerIngredientsInPizza.visibility = View.INVISIBLE
        binding.imgPizza.visibility = View.INVISIBLE
        binding.containerBox.visibility = View.VISIBLE
        binding.imgBoxBack.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(binding.imgBoxBack, View.ALPHA,0f,1f).apply {
            duration = 1200
            start()
        }

        binding.imgPizzaForAnimation.visibility = View.VISIBLE
        ObjectAnimator.ofPropertyValuesHolder(
            binding.imgPizzaForAnimation,
            PropertyValuesHolder.ofFloat(View.SCALE_Y,0.85f),
            PropertyValuesHolder.ofFloat(View.SCALE_X,0.85f)
        ).apply {
            duration = 1200
            addListener( object : AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.imgBoxFront.visibility = View.VISIBLE

                    ObjectAnimator.ofPropertyValuesHolder(
                        binding.containerBox,
                        PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, toY-fromY),
                        PropertyValuesHolder.ofFloat(View.TRANSLATION_X, toX - fromX),
                        PropertyValuesHolder.ofFloat(View.SCALE_Y,0.2f),
                        PropertyValuesHolder.ofFloat(View.SCALE_X,0.2f),
                        PropertyValuesHolder.ofFloat(View.ALPHA,1f,0f),
                        PropertyValuesHolder.ofFloat(View.ROTATION,0f,45f)
                    ).apply {
                        duration = 500
                        addListener(object : AnimatorListener{

                            override fun onAnimationStart(p0: Animator?) {
                            }

                            override fun onAnimationEnd(p0: Animator?) {
                                var fromX = binding.containerBox.x + binding.containerBox.measuredWidth/2
                                var fromY = binding.containerBox.y + binding.containerBox.measuredHeight/2
                                var toX = binding.containerCar.x + binding.containerCar.measuredWidth/2
                                var toY = binding.containerCar.y + binding.containerCar.measuredHeight/2

                                binding.containerBox.visibility = View.INVISIBLE
                                binding.imgPizzaForAnimation.visibility = View.INVISIBLE
                                binding.imgBoxFront.visibility = View.INVISIBLE
                                binding.imgBoxBack.visibility = View.INVISIBLE
                                binding.imgPizza.visibility = View.VISIBLE
                                binding.containerIngredientsInPizza.visibility = View.VISIBLE

                                ObjectAnimator.ofPropertyValuesHolder(
                                    binding.containerBox,
                                    PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, toY-fromY),
                                    PropertyValuesHolder.ofFloat(View.TRANSLATION_X, toX - fromX),
                                    PropertyValuesHolder.ofFloat(View.SCALE_Y,1f),
                                    PropertyValuesHolder.ofFloat(View.SCALE_X,1f),
                                    PropertyValuesHolder.ofFloat(View.ALPHA,0f,1f),
                                    PropertyValuesHolder.ofFloat(View.ROTATION,45f,0f)

                                ).apply{
                                    duration = 0
                                    start()
                                }

                                //add Pizza
                                var myPizza = viewModel.pizza
                                myPizza.listIngredients.addAll(viewModel.listIngredients.filter { it.selected })
                                temporalCar.listPizzaCar.add(myPizza)
                                updatePizzaCar()
                                clearIngredients()
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

    override fun onStart() {
        super.onStart()
    }

}