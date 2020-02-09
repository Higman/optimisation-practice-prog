package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.FloatGrayCode
import com.github.Higman.ga_practice_prog.GAIndividual
import com.github.Higman.simple_functiondata_library.FunctionData
import com.github.Higman.simple_functiondata_library.VecData
import kotlin.random.Random

/**
 * 二点交差による交叉器。２つの親から２つの子を生成する
 * @param geneLength 個体の遺伝子長
 * @param randSeed 交叉に利用される乱数のシード値
 */
class TwoPointCrossingUnit(
    geneLength: Int, randSeed: Long = System.currentTimeMillis()
) : AbstractCrossingUnit(2, 2, geneLength) {
    var randSeed = randSeed
        set(value) {
            field = value
            rand = Random(value)
        }
    var rand = Random(randSeed)
    override fun crossProcess(parents: List<GAIndividual>): List<GAIndividual> {
        if (parents.size != 2) throw IllegalArgumentException()
        val degree = parents.first().assignedVal.data.degree
        val firstParent = parents[0]
        val secondParent = parents[1]
        val range = firstParent.assignedVal.data.value.first().range

        // 交叉点の決定
        val marginGeneLength = parents[0].assignedVal.data.value.flatMap { it.bits }.size  // すべての変数の遺伝子をつなぎ合わせた際の遺伝子長
        var crossingPoint1 = rand.nextInt(0, marginGeneLength)  // 交叉する次元1
        var crossingPoint2 = (0..marginGeneLength).filter { crossingPoint1 != it }.toList()
            .let { it[rand.nextInt(0, it.size)] } // 交叉する次元2
        if (crossingPoint1 > crossingPoint2) crossingPoint1 =
            crossingPoint2.also { crossingPoint2 = crossingPoint1 }  // 交叉する次元の大小の入れ替え

        val children = createChildren(
            parents.first().func,
            degree,
            crossingPoint1,
            crossingPoint2,
            firstParent,
            secondParent,
            range
        )
        return children
    }

    private fun createChildren(
        func: FunctionData<Double>,
        degree: Int,
        crossingPoint1: Int,
        crossingPoint2: Int,
        firstParent: GAIndividual,
        secondParent: GAIndividual,
        range: Pair<Double, Double>
    ): List<GAIndividual> {
        val crossedData1 = mutableListOf<FloatGrayCode>()
        val crossedData2 = mutableListOf<FloatGrayCode>()

        repeat(degree) { d ->
            val bitList1 = mutableListOf<Int>()
            val bitList2 = mutableListOf<Int>()
            repeat(geneLength) { g ->
                val totalGeneNum = d * geneLength + g // 今までの繰り返し回数
                // TODO もう少し個々の処理を汎用的にしたい(３点交叉以上にも対応させたい)
                val b = if (totalGeneNum <= crossingPoint1 || crossingPoint2 < totalGeneNum) {
                    firstParent to secondParent
                } else {
                    secondParent to firstParent
                }
                bitList1.add(b.first.assignedVal.data.value[d].bits[g])
                bitList2.add(b.second.assignedVal.data.value[d].bits[g])
            }
            crossedData1.add(FloatGrayCode(bitList1, bitList1.size, range))
            crossedData2.add(FloatGrayCode(bitList2, bitList2.size, range))
        }
        // まだ所属する集合が決まってないのでfitnessValueFunctionはnull
        return listOf(
            GAIndividual(func, VecData(crossedData1), null),
            GAIndividual(func, VecData(crossedData2), null)
        )
    }

}