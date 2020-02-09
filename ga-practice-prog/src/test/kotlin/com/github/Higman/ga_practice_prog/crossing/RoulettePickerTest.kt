package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.FloatGrayCode
import com.github.Higman.ga_practice_prog.GAIndividual
import com.github.Higman.simple_functiondata_library.FunctionData
import com.github.Higman.simple_functiondata_library.VecData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.math.abs
import kotlin.math.pow

internal class RoulettePickerTest {
    lateinit var rand: Random
    @BeforeEach
    fun before() {
        rand = Random(1L)
    }

    @Test
    fun pickedOutテスト() {
        val aa = RoulettePicker(2)
        val range = Pair(-3.0, 3.0)
        val choices = mutableListOf<GAIndividual>()
        repeat(3) {
            choices.add(GAIndividual(
                FunctionData(3) { xs -> xs.sum() },
                VecData(
                    List(3) {
                        FloatGrayCode(List(4) { kotlin.random.Random.nextInt(0, 2) }, 4, range)
                    }
                ),
                choices.minFitnessFunc()
            ))
        }
        choices.sortBy { abs(it.solutionValue()) }

        val res = aa.pickedOutParents(choices).sortedBy { abs(it.solutionValue()) }
        println(choices)
        println(res)
        Assertions.assertEquals(2, res.size)
    }
}