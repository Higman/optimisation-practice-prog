package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.GAIndividual
import com.github.Higman.simple_functiondata_library.VecData
import kotlin.random.Random

class SimpleMutator(val probabilityOfOccurrence: Double, geneLength: Int, randSeed: Long = System.currentTimeMillis()) : AbstractMutator(
    geneLength
) {
    var randSeed = randSeed
        set(value) {
            field = value
            rand = Random(value)
        }

    var rand : Random = Random(randSeed)
    override fun mutateProcess(target: GAIndividual): GAIndividual {
        val mutatedData = List(target.assignedVal.data.degree) {
            var mGrayCode = target.assignedVal.data.value[it].clone()
            repeat(geneLength) { g ->
                if (rand.nextDouble() < probabilityOfOccurrence ) mGrayCode.bits[g] = mGrayCode.bits[g] xor 1
            }
            mGrayCode
        }
        return GAIndividual(target.func, VecData(mutatedData), target.fitnessValueFunction)
    }

    override fun shouldMutate(): Boolean = true
}
