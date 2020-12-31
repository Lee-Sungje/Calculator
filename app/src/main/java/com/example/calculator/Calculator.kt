package com.example.calculator

import java.math.BigDecimal

class Calculator {
    var result: BigDecimal = BigDecimal.ZERO
    var operand: BigDecimal = BigDecimal.ZERO
    var operator: String = "="
    var lastOperator: String = "+"
    var isFirstInput: Boolean = true
    var isOperatorClick: Boolean = false

    fun calculate(operator: String) {
        when (operator) {
            "=" -> result = operand
            "+" -> result += operand
            "-" -> result -= operand
            "*" -> result *= operand
            "/" -> result /= operand
            "%" -> result %= operand
        }
    }

    fun clear() {
        operand = BigDecimal.ZERO
        isFirstInput = true
    }

    fun allClear() {
        result = BigDecimal.ZERO
        operand = BigDecimal.ZERO
        operator = "="
        lastOperator = "+"
        isFirstInput = true
        isOperatorClick = false
    }
}