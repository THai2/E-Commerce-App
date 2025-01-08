package com.example.shopapp.method.functions

import java.text.NumberFormat
import java.util.*

fun currencyFormat(int: Int): String {


    val localeVN = Locale("vi", "VN")
    val currencyVN = NumberFormat.getCurrencyInstance(localeVN)

    val str1: String = currencyVN.format(int)
    return str1

}