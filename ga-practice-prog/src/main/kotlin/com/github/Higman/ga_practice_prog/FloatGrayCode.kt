package com.github.Higman.ga_practice_prog

import com.github.Higman.simple_functiondata_library.NBitGrayCode
import kotlin.math.sign

class FloatGrayCode(initVal: List<Int>, bSize: Int, range: Pair<Double, Double>) : NBitGrayCode(initVal, bSize) {
    val range: Pair<Double, Double>

    init {
        this.range = if (range.first > range.second) Pair(range.second, range.first) else range
    }

    override fun compareTo(other: NBitGrayCode): Int = (this.toDouble() - other.toDouble()).sign.toInt()

    override fun toByte(): Byte = this.toDouble().toByte()

    override fun toChar(): Char = this.toDouble().toChar()

    override fun toDouble(): Double = NumberUtil.convertDoubleInRange(super.toInt(), bitSize, range)

    override fun toFloat(): Float = this.toDouble().toFloat()

    override fun toInt(): Int = this.toDouble().toInt()

    override fun toLong(): Long = this.toDouble().toLong()

    override fun toShort(): Short = this.toDouble().toShort()

    override fun clone(): FloatGrayCode {
        return super.clone() as FloatGrayCode
    }

    override fun toString(): String {
        return "FloatGrayCode(${bits}, ${toDouble()})"
    }
}