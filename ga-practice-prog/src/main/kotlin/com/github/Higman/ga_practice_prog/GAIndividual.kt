package com.github.Higman.ga_practice_prog

import com.github.Higman.practice_prog_core.data.AbstractIndividual
import com.github.Higman.simple_functiondata_library.DataWithHistory
import com.github.Higman.simple_functiondata_library.FunctionData
import com.github.Higman.simple_functiondata_library.VecData

class GAIndividual(
    override var func: FunctionData<Double>,
    initValue: VecData<FloatGrayCode>,
    val fitnessValueFunction: (GAIndividual) -> Double
) : AbstractIndividual<FloatGrayCode>() {
    init {
        assignedVal = DataWithHistory(initValue)
    }
    val fitnessValue: Double
        get() = fitnessValueFunction(this)
}