package com.dartboard

import com.dartboard.DartThrow.Companion.allPossibleThrowsSorted

class WinnablePlayCalculatorFlatMapService : WinnablePlayCalculator {

    companion object {
        val addAllWinnableSecondThrows: (Play) -> List<Play> = { playFromFirstThrow ->
            allPossibleThrowsSorted
                .filter { playFromFirstThrow.isValidSecondThrow(it) }
                .map { throwOnBoard -> playFromFirstThrow.copy(secondThrow = throwOnBoard) }
        }

        val addAllWinnableThirdThrows: (Play) -> List<Play> = { playFromSecondThrow ->
            allPossibleThrowsSorted
                .filter {
                    playFromSecondThrow.isValidThirdThrow(it)
                }
                .map { throwOnBoard ->
                    playFromSecondThrow.copy(thirdThrow = throwOnBoard)
                }
        }
    }

    private fun addAllWinnableFirstThrows(target: Int): List<Play> =
        allPossibleThrowsSorted
            .filter { it.isValidFirstThrow(target) }
            .map { Play(firstThrow = it, target = target) }


    override fun calculateWinnablePlays(target: Int): Set<Play> =
        addAllWinnableFirstThrows(target)
            .flatMap { addAllWinnableSecondThrows(it) }
            .flatMap { addAllWinnableThirdThrows(it) }
            .toSet()

}
