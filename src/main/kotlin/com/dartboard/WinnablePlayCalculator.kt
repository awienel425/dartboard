package com.dartboard

interface WinnablePlayCalculator {

    fun calculateWinnablePlays(target:Int):Set<Play>
}