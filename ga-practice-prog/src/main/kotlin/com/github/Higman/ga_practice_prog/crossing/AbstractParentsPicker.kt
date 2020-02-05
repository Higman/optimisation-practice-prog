package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.GAIndividual
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

/**
 * 交叉に利用する親の選択器
 * @param pickedOutParentsSize 選択する親の数
 */
abstract class AbstractParentsPicker(val pickedOutParentsSize: Int) {
    init {
        require(pickedOutParentsSize >= 2) { "Value of parameter \"pickedOutParentsSize\" has to be 2 and over." }
    }

    fun pickedOutParents(choices: List<GAIndividual>): List<GAIndividual> {
        if (choices.size < pickedOutParentsSize) throw IllegalArgumentException("List size of parameter \"choices\" has to be more than value of field \"pickedOutParentsSize\".")
        val pickedOutParents = pickedOutProcess(choices)
        if (pickedOutParents.size != pickedOutParentsSize) throw IllegalPickedOutParentsSizeException()
        return pickedOutParents
    }
    protected abstract fun pickedOutProcess(choices: List<GAIndividual>): List<GAIndividual>

    class IllegalPickedOutParentsSizeException : RuntimeException()
}