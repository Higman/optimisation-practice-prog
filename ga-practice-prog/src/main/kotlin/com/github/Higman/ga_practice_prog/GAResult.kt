package com.github.Higman.ga_practice_prog

import com.github.Higman.practice_prog_core.AbstractOptimisationResult
import com.github.Higman.simple_functiondata_library.VecData

data class GAResult(
    override val bestPosition: VecData<FloatGrayCode>,
    override val solutionValue: Double,
    override val diff: Double,
    override val convergedTime: Int,
    override val time: Int
) : AbstractOptimisationResult<FloatGrayCode>() {

}