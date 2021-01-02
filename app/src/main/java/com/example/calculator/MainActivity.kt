package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.buttonMainZero.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainOne.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainTow.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainThree.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainFour.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainFive.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainSix.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainSeven.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainEight.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainNine.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainDot.setOnClickListener(numberOnClickListener)
        activityMainBinding.buttonMainPlusminussign.setOnClickListener(plusMinusSignOnClickListener)
        activityMainBinding.buttonMainClear.setOnClickListener(clearOnClickListener)
        activityMainBinding.buttonMainAllclear.setOnClickListener(allClearOnClickListener)
        activityMainBinding.buttonMainAddition.setOnClickListener(operatorOnClickListener)
        activityMainBinding.buttonMainSubtraction.setOnClickListener(operatorOnClickListener)
        activityMainBinding.buttonMainMultiplication.setOnClickListener(operatorOnClickListener)
        activityMainBinding.buttonMainDivision.setOnClickListener(operatorOnClickListener)
        activityMainBinding.buttonMainModular.setOnClickListener(operatorOnClickListener)
        activityMainBinding.buttonMainEqual.setOnClickListener(equalOnClickListener)
    }

    private val numberOnClickListener = View.OnClickListener {
        if(calculator.isFirstInput) {
            activityMainBinding.textviewMainDisplay.text = if(it.tag == ".") "0." else it.tag.toString()
            calculator.isFirstInput = it.tag == "0"
        } else {
            activityMainBinding.textviewMainDisplay.append(it.tag.toString())
        }
    }

    private val plusMinusSignOnClickListener = View.OnClickListener {
        val number = BigDecimal(activityMainBinding.textviewMainDisplay.text.toString()).negate()
        activityMainBinding.textviewMainDisplay.text = number.toString()
    }

    private val clearOnClickListener = View.OnClickListener {
        calculator.clear()
        activityMainBinding.textviewMainDisplay.text = "0"
    }

    private val allClearOnClickListener = View.OnClickListener {
        calculator.allClear()
        activityMainBinding.textviewMainDisplay.text = "0"
    }

    private val operatorOnClickListener = View.OnClickListener {
        calculator.operand = BigDecimal(activityMainBinding.textviewMainDisplay.text.toString())

        if (!calculator.isFirstInput) calculate(calculator.operator)

        calculator.operator = it.tag.toString()
        calculator.lastOperator = it.tag.toString()
        calculator.isFirstInput = true
        calculator.isOperatorClick = true
    }

    private val equalOnClickListener = View.OnClickListener {
        if (calculator.isFirstInput && calculator.isOperatorClick) {
            if (calculator.operator != "=") calculator.operand = calculator.result
            calculate(calculator.lastOperator)
        } else {
            if (calculator.operator == "=") {
                calculator.result = BigDecimal(activityMainBinding.textviewMainDisplay.text.toString())
                calculate(calculator.lastOperator)
            } else {
                calculator.operand = BigDecimal(activityMainBinding.textviewMainDisplay.text.toString())
                calculate(calculator.operator)
            }
        }

        calculator.operator = it.tag.toString()
        calculator.isFirstInput = true
    }

    private fun calculate(operator: String) {
        try {
            calculator.calculate(operator)
            activityMainBinding.textviewMainDisplay.text = calculator.result.toString()
        } catch (e: ArithmeticException) {
            calculator.allClear()
            activityMainBinding.textviewMainDisplay.text = "0"
            Toast.makeText(this, "0으로 나눌 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}