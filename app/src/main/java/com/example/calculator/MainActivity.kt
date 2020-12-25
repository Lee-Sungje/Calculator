package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textviewMainDisplay.text = calculator.result.toString()

        binding.buttonMainZero.setOnClickListener(numberOnClickListener)
        binding.buttonMainOne.setOnClickListener(numberOnClickListener)
        binding.buttonMainTow.setOnClickListener(numberOnClickListener)
        binding.buttonMainThree.setOnClickListener(numberOnClickListener)
        binding.buttonMainFour.setOnClickListener(numberOnClickListener)
        binding.buttonMainFive.setOnClickListener(numberOnClickListener)
        binding.buttonMainSix.setOnClickListener(numberOnClickListener)
        binding.buttonMainSeven.setOnClickListener(numberOnClickListener)
        binding.buttonMainEight.setOnClickListener(numberOnClickListener)
        binding.buttonMainNine.setOnClickListener(numberOnClickListener)
        binding.buttonMainClear.setOnClickListener(clearOnClickListener)
    }

    private val numberOnClickListener = View.OnClickListener {
        val buttonNumber = findViewById<Button>(it.id)

        calculator.operand = (calculator.operand * BigDecimal(10)) + BigDecimal(buttonNumber.text.toString())
        binding.textviewMainDisplay.text = calculator.operand.toString()
    }

    private val clearOnClickListener = View.OnClickListener {
        calculator.clear()
        binding.textviewMainDisplay.text = calculator.operand.toString()
    }
}