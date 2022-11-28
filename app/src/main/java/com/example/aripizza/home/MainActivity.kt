package com.example.aripizza

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Intent
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.viewpager2.widget.ViewPager2
import com.example.aripizza.databinding.ActivityMainBinding
import com.example.aripizza.detail.DetailPizzaActivity
import com.example.aripizza.home.MainViewModel
import com.example.aripizza.home.adapter.AdapterListPizzas
import com.example.aripizza.home.productCar.ProductCarActivity
import com.example.aripizza.home.productCar.temporalCar
import com.example.aripizza.model.Pizza
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private val adapterListPizzas: AdapterListPizzas by lazy {
        AdapterListPizzas(viewModel.listPizzas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        setListeners()
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
    }

    private fun setItemPizza(position: Int) {
        addAmount(viewModel.listPizzas[position].price)
        addNewName(viewModel.listPizzas[position].name)
        binding.ratingBar.rating = viewModel.listPizzas[position].rating
        viewModel.listPizzas.first { it.selected }.selected = false
        viewModel.listPizzas[position].selected = true
        binding.imgPizzaForAnimation.setImageResource(viewModel.listPizzas[position].image)
        binding.imgPizzaForAnimation.visibility = View.INVISIBLE
    }

    private fun addPizzaToCar() {
        var fromX = binding.containerBox.x + binding.containerBox.measuredWidth/2
        var fromY = binding.containerBox.y + binding.containerBox.measuredHeight/2
        var toX = binding.containerCar.x + binding.containerCar.measuredWidth/2
        var toY = binding.containerCar.y + binding.containerCar.measuredHeight/2

        binding.vpPizza.visibility = View.INVISIBLE
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
                                binding.vpPizza.visibility = View.VISIBLE
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
                                temporalCar.listPizzaCar.add(viewModel.listPizzas.first { it.selected })
                                updatePizzaCar()
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


    private fun setAdapter() {
        binding.vpPizza.apply {
            adapter = adapterListPizzas
            offscreenPageLimit = 3
            setPageTransformer(CircularTransform(3))
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                var initPosition = 0.0
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)

                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setItemPizza(position)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    println(positionOffset)
                    if (positionOffset - initPosition < 0) {
                        ingredientsRotationRight(positionOffset + position)
                    } else if (positionOffset - initPosition > 0) {
                        ingredientsRotationLeft(positionOffset + position)
                    }
                }
            })
        }

        adapterListPizzas.actionSelect = {
            var intent = Intent(this@MainActivity, DetailPizzaActivity::class.java)
            var options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@MainActivity,
                Pair(adapterListPizzas.getViewByPosition(viewModel.listPizzas.indexOfFirst { it.selected }).itemBinding.imgPizza,"img_pizza"),
                Pair(binding.btnAdd, "btn_add"),
                Pair(binding.containerAmount, "container_amount")
            )
            intent.putExtra("pizza", viewModel.listPizzas.first { it.selected })
            startActivity(intent, options.toBundle())
        }
    }

    fun ingredientsRotationLeft(position: Float) {
        println("izquierda")
        if (position * 45 > 1f)
            ObjectAnimator.ofFloat(binding.imgIngredients, View.ROTATION, position * 45).apply {
                duration = 0

                start()
            }
    }

    fun ingredientsRotationRight(position: Float) {
        println("derecha")
        if (position * 45 > 1f)
            ObjectAnimator.ofFloat(binding.imgIngredients, View.ROTATION, position * -45).apply {
                duration = 0
                start()
            }
    }


    private fun addAmount(newAmount: Double) {
        var distanceY = 55f
        ObjectAnimator.ofPropertyValuesHolder(
            binding.containerAmountBack,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0F, -distanceY),
            PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)
        ).apply {
            duration = 200
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.containerAmountBack.alpha = 0f
                    binding.textTotalBack.text = newAmount.toString()
                    ObjectAnimator.ofFloat(
                        binding.containerAmountBack,
                        View.TRANSLATION_Y,
                        0f,
                        +distanceY
                    ).apply {
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
            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, distanceY, 0f),
            PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
        ).apply {
            duration = 200
            addListener(object : Animator.AnimatorListener {
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


    private fun addNewName(newName: String) {
        var distanceY = 55f
        ObjectAnimator.ofPropertyValuesHolder(
            binding.textNamePizzaBack,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0F, -distanceY),
            PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)
        ).apply {
            duration = 200
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    binding.textNamePizzaBack.alpha = 0f
                    binding.textNamePizzaBack.text = newName
                    ObjectAnimator.ofFloat(
                        binding.textNamePizzaBack,
                        View.TRANSLATION_Y,
                        0f,
                        +distanceY
                    ).apply {
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
            binding.textNamePizza,
            PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, distanceY, 0f),
            PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
        ).apply {
            duration = 200
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    binding.textNamePizza.text = newName

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        updatePizzaCar()
        super.onStart()
    }
}

class CircularTransform(private val offScreenPageLimit: Int) : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        var default_alpha = 1f
        var scale_factor = 0.4f
        var deltaY = page.height / 3.5f
        page.apply {
            when {
                position < 0f -> {
                    // alpha = default_alpha + position
                    translationX = -position * width / 2  //(position + 1)*(-width/2)
                    translationY = -position * deltaY
                    scaleX = (position + 1) * (1 - scale_factor) + scale_factor
                    scaleY = (position + 1) * (1 - scale_factor) + scale_factor
                    rotation = position * 90
                }

                position == 0f -> {
                    alpha = default_alpha
                }

                position > 0f -> {
                    //alpha = default_alpha - position
                    rotation = position * 90
                    translationX = -position * (width / 2)
                    translationY = position * deltaY
                    scaleX = (position) * (scale_factor - 1) + 1
                    scaleY = (position) * (scale_factor - 1) + 1
                }
            }
        }
    }
}