package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.GAIndividual

/**
 * 個体の突然変異器
 * @param geneLength 個体の遺伝子長
 */
abstract class AbstractMutator(val geneLength: Int) {
    init {
        require(geneLength >= 1) { "Value of parameter \"geneLength\" has to be 1 and over." }
    }
    protected abstract fun mutateProcess(target: GAIndividual): GAIndividual
    protected abstract fun shouldMutate(): Boolean
    fun mutate(targets: GAIndividual): GAIndividual? = if (shouldMutate()) mutateProcess(targets) else null
}