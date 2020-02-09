package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.GAIndividual

class CrossingProcessor(
    var parentsPicker: AbstractParentsPicker,
    var crossingUnit: AbstractCrossingUnit,
    var mutetor: AbstractMutator,
    var sifter: AbstractGeneSifter,
    val crossSize: Int
) {
    var population: List<GAIndividual>? = null

    fun resister(p: List<GAIndividual>) {
        population = p
    }

    fun updateByCrossing() {
        val currentPopulation = population ?: throw NullPointerException()

        // 子の生成
        val childPopulation = List(crossSize) {
            val p = parentsPicker.pickedOutParents(currentPopulation)
            val newInd = crossingUnit.cross(p)
            newInd.map { nI -> requireNotNull(mutetor.mutate(nI)) }
        }.flatten()
        // 篩
        // 評価関数を親と子が所属する集合についての関数に更新
        val populationParentsAndChildren = (currentPopulation + childPopulation).let { p ->
            p.map { it.apply { this.fitnessValueFunction = p.minFitnessFunc() } }
        }
        val newPopulation = sifter.sift(populationParentsAndChildren)
        // 更新処理
        repeat(currentPopulation.size) {
            requireNotNull(population)[it].assignedVal.data = newPopulation[it].assignedVal.data
        }
    }
}