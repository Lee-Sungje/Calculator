package com.example.calculator

import java.math.BigDecimal
import java.math.MathContext

class Calculator {
    private val mathContext = MathContext.DECIMAL64
    var result: BigDecimal = BigDecimal(0, mathContext)
    var operand: BigDecimal = BigDecimal(0, mathContext)
    var operator: String = "="
    var lastOperator: String = "+"
    var isFirstInput: Boolean = true
    var isOperatorClick: Boolean = false

    fun calculate(operator: String) {
        when (operator) {
            "=" -> result = operand
            "+" -> result = result.add(operand, mathContext)
            "-" -> result = result.subtract(operand, mathContext)
            "*" -> result = result.multiply(operand, mathContext)
            "/" -> result = result.divide(operand, mathContext)
            "%" -> result = result.remainder(operand, mathContext)
        }
    }

    fun clear() {
        operand = BigDecimal(0, mathContext)
        isFirstInput = true
    }

    fun allClear() {
        result = BigDecimal(0, mathContext)
        operand = BigDecimal(0, mathContext)
        operator = "="
        lastOperator = "+"
        isFirstInput = true
        isOperatorClick = false
    }
}