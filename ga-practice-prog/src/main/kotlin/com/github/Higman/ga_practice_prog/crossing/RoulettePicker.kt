package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.GAIndividual
import kotlin.random.Random

/**
 * ルーレット選択による親の選択器
 * @param pickedOutParentsSize 親を選択する元の集合
 * @param randSeed 選択に利用する乱数のシード値
 */
class RoulettePicker(pickedOutParentsSize: Int, randSeed: Long = System.currentTimeMillis()) :
    AbstractParentsPicker(pickedOutParentsSize) {
    private var randSeed: Long = randSeed
        set(value) {
            field = value
            rand = Random(value)
        }
    private var rand: Random = Random(randSeed)

    override fun pickedOutProcess(choices: List<GAIndividual>): List<GAIndividual> {
        val targetChoicesIndex = choices.indices.toMutableList()
        var fitnessValueTotal = calcFitnessValueTotal(choices)
        val pickedOutParents = mutableListOf<GAIndividual>()
        repeat(pickedOutParentsSize) {
            var tempRandForSelection = fitnessValueTotal * rand.nextDouble()
            val selectedIndex = targetChoicesIndex.indexOfFirst {
                if (tempRandForSelection < choices[it].fitnessValue) return@indexOfFirst true
                tempRandForSelection -= choices[it].fitnessValue
                false
            }
            val idx = targetChoicesIndex.removeAt(
                if (selectedIndex >= 0) {
                    selectedIndex
                } else 0  // ルーレット選択で定まらなかった場合、先頭要素を選択
            )
            pickedOutParents.add(choices[idx])
            fitnessValueTotal -= choices[idx].fitnessValue
        }
        return pickedOutParents
    }

    /**
     * 適応値の合計の算出
     */
    private fun calcFitnessValueTotal(choices: List<GAIndividual>): Double =
        choices.fold(0.0) { a, gaIndividual ->
            a + gaIndividual.fitnessValue
        }
}