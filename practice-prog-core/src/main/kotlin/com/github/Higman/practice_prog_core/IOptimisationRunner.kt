package com.github.Higman.practice_prog_core

interface IOptimisationRunner {
    fun run() : Boolean
    fun step() : Boolean
    fun result() : AbstractOptimisationResult<*>
}