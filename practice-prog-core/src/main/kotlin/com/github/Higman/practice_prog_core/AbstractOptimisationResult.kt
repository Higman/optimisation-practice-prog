package com.github.Higman.practice_prog_core

import com.github.Higman.simple_functiondata_library.NBitGrayCode
import com.github.Higman.simple_functiondata_library.VecData

abstract class AbstractOptimisationResult<T: Number> {
    abstract val bestPosition: VecData<T>
    abstract val solutionValue: Double
    abstract val diff: Double
    abstract val convergedTime: Int
    abstract val time: Int
}