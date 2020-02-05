package com.github.Higman.ga_practice_prog

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.RoundingMode

internal class NumberUtilTest {
    @Test
    fun 実数値への変換テスト() {
        val range = Pair(-3.0, 3.0)
        assertEquals(
            NumberUtil.convertDoubleInRange(12, 4, range).toBigDecimal().setScale(
                5,
                RoundingMode.HALF_UP
            ).toDouble(), 1.8
        )
    }
}