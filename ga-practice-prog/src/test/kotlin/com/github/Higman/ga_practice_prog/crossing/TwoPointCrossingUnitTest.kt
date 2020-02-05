package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.FloatGrayCode
import com.github.Higman.ga_practice_prog.GAIndividual
import com.github.Higman.simple_functiondata_library.FunctionData
import com.github.Higman.simple_functiondata_library.VecData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class TwoPointCrossingUnitTest {

    lateinit var rand: Random
    @BeforeEach
    fun before() {
        rand = Random(1L)
    }

    private val range = Pair(-3.0, 3.0)

    @Test
    fun crossProcess() {
        val parents = listOf(
            GAIndividual(
                FunctionData(1) { xs -> xs.sum() },
                VecData(
                    listOf(FloatGrayCode(mutableListOf(0, 0, 0), 4, range))
                ),
                { 1.0 }
            ),
            GAIndividual(
                FunctionData(1) { xs -> xs.sum() },
                VecData(
                    listOf(FloatGrayCode(mutableListOf(1, 1, 1, 1), 4, range))
                ),
                { 1.0 }
            )
        )
        val tpc = TwoPointCrossingUnit(4, 0L)
        Assertions.assertEquals(mutableListOf(0,0,1,0), tpc.cross(parents)[0].assignedVal.data.value[0].bits)
    }
}