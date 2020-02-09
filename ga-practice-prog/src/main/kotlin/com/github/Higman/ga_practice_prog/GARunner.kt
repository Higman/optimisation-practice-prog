package com.github.Higman.ga_practice_prog

import com.github.Higman.ga_practice_prog.crossing.*
import com.github.Higman.practice_prog_core.AbstractOptimisationResult
import com.github.Higman.simple_functiondata_library.FunctionData
import com.github.Higman.simple_functiondata_library.VecData
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.random.Random

/**
 *
 */
class GARunner(
    val func: FunctionData<Double>,
    val initValues: List<VecData<FloatGrayCode>>,
    val correctValue: Double,
    val timeMax: Int,
    randSeed: Long = System.currentTimeMillis()
) :
    AbstractOptimisationRunner() {
    var randSeed = randSeed
        set(value) {
            field = value
            rand = Random(value)
        }

    var rand = Random(randSeed)
    // 歴代で最良の結果
    var bestResultIndividualInHistory: GAIndividual? = null
        private set

    private val crossingProcessor: CrossingProcessor = run {
        // 初期値引数から遺伝子長を調べる
        val gLen = if (initValues.any {
                it.value.any { it.bitSize == initValues.first().value.first().bitSize }
            }) initValues.first().value.first().bitSize else throw IllegalArgumentException()  // 全ての要素の遺伝子長が同じかチェック
        CrossingProcessor(
            RoulettePicker(2, randSeed),
            TwoPointCrossingUnit(gLen, randSeed),
            SimpleMutator(0.005, gLen, randSeed),
            SimpleGeneSifter(initValues.size),
            10
        )
    }

    fun init() {
        time = 0
        convergedTime = 0
        val population: MutableList<GAIndividual> = mutableListOf()
        repeat(initValues.size) {
            population.add(GAIndividual(func, initValues[it], population.minFitnessFunc()))
        }
        crossingProcessor.resister(population)
    }

    override fun run(): Boolean {
        init()
        run {
            while (time < timeMax) {
                if (step()) return@run
                println(time)
            }
            return false
        }
        return true
    }

    // 収束した世代
    var convergedTime = 0
        private set

    override fun step(): Boolean {
        crossingProcessor.updateByCrossing()
        time += 1
        return isReached(correctValue).apply {
            updateBestInHistory()
        }
    }

    // 最良値の更新
    private fun updateBestInHistory() {
        val br = getBestResultIndividual()  // 現状の集合の最良値を取得
        // 最適値が更新されたか判定
        if (br.solutionValue() < (bestResultIndividualInHistory?.solutionValue() ?: Double.MAX_VALUE)) {
            convergedTime = time
            bestResultIndividualInHistory = br.clone()
        }
    }

    private val Accuracy = 10e-5

    //
    fun isReached(cv: Double) = crossingProcessor.population?.any { (it.solutionValue() - cv).absoluteValue < Accuracy }
        ?: throw UninitializedPropertyAccessException()

    fun getBestResultIndividual(): GAIndividual {
        var result: GAIndividual? = null
        var bestAbsDiff: Double = Double.MAX_VALUE
        requireNotNull(crossingProcessor.population).forEach {
            val absDiff = (it.solutionValue() - correctValue).absoluteValue
            if (absDiff < bestAbsDiff) {
                result = it
                bestAbsDiff = absDiff
            }
        }
        return requireNotNull(result)
    }

    override fun result(): AbstractOptimisationResult<FloatGrayCode> {
        return requireNotNull(bestResultIndividualInHistory).let {
            GAResult(
                it.currentXData(),
                it.solutionValue(),
                it.solutionValue() - correctValue,
                convergedTime,
                time
            )
        }
    }
}