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
        val targetChoices = choices.toMutableList()
        var fitnessValueTotal = calcFitnessValueTotal(choices)
        val pickedOutParents = mutableListOf<GAIndividual>()
        repeat(pickedOutParentsSize) {
            val randForSelection = rand.nextDouble()
            var tempRandForSelection = fitnessValueTotal * randForSelection
            val selectedIndex = targetChoices.indexOfFirst {
                if (tempRandForSelection < it.fitnessValue) return@indexOfFirst true
                tempRandForSelection -= it.fitnessValue
                false
            }
            if (selectedIndex >= 0) {
                pickedOutParents.add(targetChoices.removeAt(selectedIndex))
            } else {  // ルーレット選択で定まらなかった場合、先頭要素を選択
                pickedOutParents.add(targetChoices.removeAt(0))
            }
            fitnessValueTotal -= randForSelection
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