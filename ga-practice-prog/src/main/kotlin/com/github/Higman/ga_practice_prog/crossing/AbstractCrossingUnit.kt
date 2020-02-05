package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.GAIndividual

/**
 * 個体の交叉器
 * @param parentsSize 交叉に使用する親の数
 * @param childSize 交叉で誕生する子の数
 * @param geneLength 個体の遺伝子長
 */
abstract class AbstractCrossingUnit(val parentsSize: Int, val childSize: Int, val geneLength: Int) {
    init {
        require(parentsSize >= 2) { "Value of parameter \"parentsSize\" has to be 2 and over." }
        require(geneLength >= 1) { "Value of parameter \"geneLength\" has to be 1 and over." }
    }
    fun cross(parents: List<GAIndividual>) : List<GAIndividual> {
        if (parentsSize != parents.size) throw IllegalParentsSizeForCrossing()
        val c = crossProcess(parents)
        if (childSize != c.size) throw IllegalParentsSizeForCrossing()
        return c
    }

    protected abstract fun crossProcess(parents: List<GAIndividual>): List<GAIndividual>

    class IllegalParentsSizeForCrossing: RuntimeException()
}