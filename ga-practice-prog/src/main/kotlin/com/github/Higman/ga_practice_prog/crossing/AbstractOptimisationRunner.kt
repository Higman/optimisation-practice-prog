package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.practice_prog_core.IOptimisationRunner

abstract class AbstractOptimisationRunner: IOptimisationRunner {
    var time : Int = 0
        protected set(value) {
            field = value
        }
}