package com.github.Higman.practice_prog_core.data

import com.github.Higman.simple_functiondata_library.DataWithHistory
import com.github.Higman.simple_functiondata_library.FunctionData
import com.github.Higman.simple_functiondata_library.NBitGrayCode
import com.github.Higman.simple_functiondata_library.VecData

abstract class AbstractIndividual<T: Number> {
    open lateinit var func: FunctionData<Double>
    open lateinit var assignedVal: DataWithHistory<VecData<T>>

    fun solutionValue() = func.calc(VecData(assignedVal.data.value.map { it.toDouble() }))
    fun currentXData() = assignedVal.data.copy()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractIndividual<T>

        if (func != other.func) return false
        if (assignedVal != other.assignedVal) return false

        return true
    }

    override fun hashCode(): Int {
        var result = func.hashCode()
        result = 31 * result + assignedVal.hashCode()
        return result
    }

    override fun toString(): String {
        return "${javaClass.name.substringAfterLast(".")}(assignedVal=$assignedVal)"
    }
}