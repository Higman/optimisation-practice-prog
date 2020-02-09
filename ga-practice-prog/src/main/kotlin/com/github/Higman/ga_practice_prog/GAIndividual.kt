package com.github.Higman.ga_practice_prog

import com.github.Higman.practice_prog_core.data.AbstractIndividual
import com.github.Higman.simple_functiondata_library.DataWithHistory
import com.github.Higman.simple_functiondata_library.FunctionData
import com.github.Higman.simple_functiondata_library.NBitGrayCode
import com.github.Higman.simple_functiondata_library.VecData
import java.lang.RuntimeException

class GAIndividual(
    override var func: FunctionData<Double>,
    initValue: VecData<FloatGrayCode>,
    var fitnessValueFunction: ((GAIndividual) -> Double)?
) : AbstractIndividual<FloatGrayCode>(), Cloneable {
    init {
        assignedVal = DataWithHistory(initValue)
    }
    val fitnessValue: Double
        get() = requireNotNull(fitnessValueFunction)(this)

    public override fun clone(): GAIndividual {
        val g = kotlin.runCatching {
            super.clone() as GAIndividual
        }.fold(
            onSuccess = { it },
            onFailure = { throw RuntimeException("Failed to clone in ${this::class.qualifiedName}") }
        )
        g.fitnessValueFunction = fitnessValueFunction
        g.assignedVal = assignedVal.clone()
        return g
    }
}