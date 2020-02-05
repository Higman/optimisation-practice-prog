package com.github.Higman.ga_practice_prog

import kotlin.math.pow

object NumberUtil {
    fun convertDoubleInRange(i : Int, bSize: Int, range: Pair<Double, Double>) : Double {
        val max = range.second
        val min = range.first
        return ((max-min)*i)/(2.0.pow(bSize)-1) + min
    }
}