package com.github.Higman.ga_practice_prog

import com.github.Higman.simple_functiondata_library.FunctionData
import com.github.Higman.simple_functiondata_library.VecData
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.random.Random

val Functions = listOf(
    "Rastrigin func." to { xs: List<Double> -> xs.sumByDouble { x -> x.pow(2) - 10 * cos(2 * PI * x) + 10 } },
    "Rosenbrock func." to { xs: List<Double> ->
        xs.drop(1).zip(xs.dropLast(1))
            .sumByDouble { 100 * (it.first - it.second.pow(2)).pow(2) + (it.second - 1).pow(2) }
    }
)
private val GeneLengthInitValue = 4

val FuncDegrees = listOf(64)
val Range = Pair(-2.0, 2.0)

private val IndividualSize = 30

// 初期値行列変数
val initVecMap: HashMap<DegreeAndGeneLength, List<VecData<FloatGrayCode>>> =
    object : HashMap<DegreeAndGeneLength, List<VecData<FloatGrayCode>>>() {
        override fun get(key: DegreeAndGeneLength): List<VecData<FloatGrayCode>> {
            val data = super.get(key)
            return data
                ?: run {
                    val n = List(IndividualSize) {
                        VecData(List(key.d) {
                            createRandomFloatGrayCode(key.gl, Range)
                        })
                    }
                    set(key, n)
                    n
                }
        }
    }
    @Synchronized get

// 乱数シード値変数
val seedMap : HashMap<DegreeAndGeneLength, Long> =
    object : HashMap<DegreeAndGeneLength, Long>() {
        override fun get(key: DegreeAndGeneLength): Long {
            val data = super.get(key)
            return data
                ?: run {
                    val n = System.currentTimeMillis()
                    set(key, n)
                    n
                }
        }
    }
    @Synchronized get

fun main() {
    FuncDegrees.forEach { d ->
        Functions.forEach { f ->
            gaRun(d, f)
        }
    }
}

private fun gaRun(
    d: Int,
    f: Pair<String, (List<Double>) -> Double>
    ) {
    var geneLength = GeneLengthInitValue
    while (true) {
        val param = DegreeAndGeneLength(d, geneLength)
        val init = requireNotNull(initVecMap[param])
        val seed = requireNotNull(seedMap[param])
        val gr = GARunner(FunctionData(d, f.second), init, 0.0, 1000)
        gr.run()
        val r = gr.result()
        println("${f.first} ${d} ${geneLength}: $r")
        if (r.diff < 10e-5) break
        geneLength++
    }
}

fun createRandomFloatGrayCode(bSize: Int, range: Pair<Double, Double>): FloatGrayCode {
    if (range.first > range.second) throw IllegalArgumentException()
    return FloatGrayCode(List(bSize) { Random.nextInt(0, 2) }, bSize, range)
}

data class DegreeAndGeneLength(val d: Int, val gl: Int)