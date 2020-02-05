package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.GAIndividual

/**
 * 次世代の個体の選択器
 * @param geneSize 個体の数
 */
abstract class AbstractGeneSifter(val geneSize: Int) {
    init {
        if (geneSize < 1) throw IllegalArgumentException()
    }

    abstract fun sift(indList: List<GAIndividual>): List<GAIndividual>
}