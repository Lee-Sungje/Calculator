package com.example.calculator

import java.math.BigDecimal

class Calculator {
    var result: BigDecimal = BigDecimal(0)
    var operand: BigDecimal = BigDecimal(0)

    fun addition() {}
    fun subtraction() {}
    fun multiplication() {}
    fun division() {}
    fun modular() {}
    fun clear() {
        this.operand = BigDecimal(0)
    }
    fun allClear() {}
}