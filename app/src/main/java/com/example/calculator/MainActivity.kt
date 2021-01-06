package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val calculator = Calculator()
    private val mathContext = MathContext.DECIMAL64
    private val minimumRange = BigDecimal(1.0E-8, mathContext)
    private val maximumRange = BigDecimal(1.0E+9 - 1, mathContext)
    private val decimalFormat = DecimalFormat("#,##0.########")
    private val exponentialFormat = DecimalFormat("0.######E0")

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
        activityMainBinding.buttonMainDot.setOnClickListener(dotOnClickListener)
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
            activityMainBinding.textviewMainDisplay.text = it.tag.toString()
            calculator.isFirstInput = it.tag == "0"
        } else {
            val string = activityMainBinding.textviewMainDisplay.text.toString() + it.tag.toString()
            val number = parse(string)

            activityMainBinding.textviewMainDisplay.text = if (isDecimal(activityMainBinding.textviewMainDisplay.text.toString()) && string.length <= 10) {
                string
            } else if (isInRange(number)) {
                format(number, decimalFormat)
            } else {
                activityMainBinding.textviewMainDisplay.text
            }
        }

    }

    private val dotOnClickListener = View.OnClickListener {
        if(!isDecimal(activityMainBinding.textviewMainDisplay.text.toString())) {
            calculator.isFirstInput = false
            val string = activityMainBinding.textviewMainDisplay.text.toString() + it.tag.toString()
            activityMainBinding.textviewMainDisplay.text = string
        }
    }

    private val plusMinusSignOnClickListener = View.OnClickListener {
        val number = parse(activityMainBinding.textviewMainDisplay.text.toString()).negate()
        if (!isZero(number)) {
            activityMainBinding.textviewMainDisplay.text = if (isInRange(number)) {
                format(number, decimalFormat)
            } else {
                format(number, exponentialFormat)
            }
        }
    }

    private val clearOnClickListener = View.OnClickListener {
        calculator.clear()
        activityMainBinding.textviewMainDisplay.text = format(calculator.operand, decimalFormat)
    }

    private val allClearOnClickListener = View.OnClickListener {
        calculator.allClear()
        activityMainBinding.textviewMainDisplay.text = format(calculator.result, decimalFormat)
    }

    private val operatorOnClickListener = View.OnClickListener {
        try {
            calculator.operand = parse(activityMainBinding.textviewMainDisplay.text.toString())
        } catch (e: NumberFormatException) {
            calculator.allClear()
            makeToast("오버플로우")
        } catch (e: Exception) {
            calculator.allClear()
            makeToast("오류")
        }

        if (!calculator.isFirstInput) calculate(calculator.operator)

        calculator.operator = it.tag.toString()
        calculator.lastOperator = it.tag.toString()
        calculator.isFirstInput = true
        calculator.isOperatorClick = true
    }

    private val equalOnClickListener = View.OnClickListener {
        try {
            val number = parse(activityMainBinding.textviewMainDisplay.text.toString())

            if (calculator.isFirstInput && calculator.isOperatorClick && !isZero(number)) {
                if (calculator.operator != "=") calculator.operand = calculator.result
                calculate(calculator.lastOperator)
            } else {
                if (calculator.operator == "=") {
                    calculator.result = number
                    calculate(calculator.lastOperator)
                } else {
                    calculator.operand = number
                    calculate(calculator.operator)
                }
            }
        } catch (e: NumberFormatException) {
            calculator.allClear()
            activityMainBinding.textviewMainDisplay.text = format(calculator.result, decimalFormat)
            makeToast("오버플로우")
        } catch (e: Exception) {
            calculator.allClear()
            activityMainBinding.textviewMainDisplay.text = format(calculator.result, decimalFormat)
            makeToast("오류")
        }

        calculator.operator = it.tag.toString()
        calculator.isFirstInput = true
    }

    private fun calculate(operator: String) {
        try {
            calculator.calculate(operator)
        } catch (e: ArithmeticException) {
            calculator.allClear()
            makeToast("0으로 나눌 수 없습니다.")
        } catch (e: NumberFormatException) {
            calculator.allClear()
            makeToast("오버플로우")
        } catch (e: Exception) {
            calculator.allClear()
            makeToast("오류")
        } finally {
            activityMainBinding.textviewMainDisplay.text = if (isInRange(calculator.result) || isZero(calculator.result)) {
                format(calculator.result, decimalFormat)
            } else {
                format(calculator.result, exponentialFormat)
            }
        }
    }

    private fun makeToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    private fun isInRange(number: BigDecimal) = number.abs(mathContext) in minimumRange..maximumRange
    private fun isZero(number: BigDecimal) = number == BigDecimal.ZERO || number.toDouble() == 0.0
    private fun isDecimal(string: String) = string.contains(".")
    private fun format(number: BigDecimal, format: DecimalFormat) = format.format(number)
    private fun parse(string: String) = BigDecimal(decimalFormat.parse(string)?.toString(), mathContext)
}