package me.omega.mythcommons

import java.text.NumberFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

private val M = arrayOf("", "M", "MM", "MMM")
private val C = arrayOf("", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM")
private val X = arrayOf("", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC")
private val I = arrayOf("", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX")

fun formatted(number: Double): String {
    if (number < 1000) {
        return floor(number).toInt().toString()
    }
    val suffixes = arrayOf(
        " ", "K", "M", "B", "T", "Q", "Qn", "Sx", "Sp", "Oc", "No", "De", "UnD", "DoD", "TrD",
        "QaD", "QiD", "SxD", "SpD", "OcD", "NoD"
    )
    val exp = (log10(number) / 3).toInt()
    val suffix = suffixes[exp]
    val value = number / 10.0.pow((exp * 3).toDouble())
    return String.format("%.2f%s", value, suffix)
}

fun pretty(number: Double): String? {
    return NumberFormat.getNumberInstance().format(number)
}

fun roman(number: Int): String {
    return M[number / 1000] + C[number % 1000 / 100] + X[number % 100 / 10] + I[number % 10]
}