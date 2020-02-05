package com.github.Higman.ga_practice_prog.crossing

import com.github.Higman.ga_practice_prog.GAIndividual
import kotlin.math.pow
import kotlin.math.sqrt

fun List<GAIndividual>.minFitnessFunc(): (GAIndividual) -> Double = {
    val ave = this.sumByDouble { it.solutionValue() } / this.size
    val std = sqrt(this.map { (it.solutionValue() - ave).pow(2) }.sumByDouble { it } / this.size)
    50 - 10 * ((it.solutionValue() - ave) / std)
}

fun List<GAIndividual>.maxFitnessFunc(): (GAIndividual) -> Double = {
    val ave = this.sumByDouble { it.solutionValue() } / this.size
    val std = sqrt(this.map { (it.solutionValue() - ave).pow(2) }.sumByDouble { it } / this.size)
    50 - 10 * ((it.solutionValue() + ave) / std)
}