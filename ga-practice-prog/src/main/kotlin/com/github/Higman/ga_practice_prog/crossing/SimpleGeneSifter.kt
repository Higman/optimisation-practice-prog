package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.GAIndividual

/**
 * 適応値の高い個体を次世代に残す選択器
 * @param geneSize 次世代に残す個体数
 */
class SimpleGeneSifter(geneSize: Int) : AbstractGeneSifter(geneSize) {
    override fun sift(indList: List<GAIndividual>): List<GAIndividual>  = indList.sortedByDescending { it.fitnessValue }.take(geneSize)
}