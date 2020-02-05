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
    private val crossingProcessor: CrossingProcessor = run {
        // 初期値引数から遺伝子長を調べる
        val gLen = if (initValues.any {
                it.value.any { it.bitSize == initValues.first().value.first().bitSize }
            }) initValues.first().value.first().bitSize else throw IllegalArgumentException()
        CrossingProcessor(
            RoulettePicker(2, randSeed),
            TwoPointCrossingUnit(gLen, randSeed),
            SimpleMutator(0.0001, gLen, randSeed),
            SimpleGeneSifter(30),
            20
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
            }
            return false
        }
        return true
    }

    // ある時点での最適値差分
    private var bestResultDiffAtSomePoint = Double.MAX_VALUE
    // 収束した世代
    var convergedTime = 0
        private set

    override fun step(): Boolean {
        crossingProcessor.updateByCrossing()
        time += 1
        return isReached(correctValue).apply {
            checkConvergence()
        }
    }

    // 収束の監視
    private fun checkConvergence() {
        val br = getBestResultIndividual()
        // 最適値が更新されたか判定
        val tempDiff = abs(br.solutionValue() - correctValue)
        if (bestResultDiffAtSomePoint > tempDiff) {
            convergedTime = time
            bestResultDiffAtSomePoint = tempDiff
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
        val best = getBestResultIndividual()
        return GAResult(
            best.currentXData(),
            best.solutionValue(),
            best.solutionValue() - correctValue,
            convergedTime,
            time
        )
    }
}