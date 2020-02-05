package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.FloatGrayCode
import com.github.Higman.ga_practice_prog.GAIndividual
import com.github.Higman.simple_functiondata_library.FunctionData
import com.github.Higman.simple_functiondata_library.VecData
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class SimpleMutatorTest {
    lateinit var rand: Random
    @BeforeEach
    fun before() {
        rand = Random(1L)
    }

    private val range = Pair(-3.0, 3.0)

    @Test
    fun mutateProcess() {
        val g = GAIndividual(
            FunctionData(2) { xs -> xs.sum() },
            VecData(listOf(
                FloatGrayCode(mutableListOf(1, 0, 1), 4, range),
                FloatGrayCode(mutableListOf(0,0,0), 4, range)
            )),
            { 1.0 }
        )
        println(SimpleMutator(1.0, 4, 1L).mutate(g))
    }
}