package com.citi.interview


val lookupMap by lazy {
    mutableListOf<ThreeThrows>().also { allPossiblePlays ->
        for (firstThrow in DartThrow.allPossibleThrowsSorted) {
            for (secondThrow in DartThrow.allPossibleThrowsSorted) {
                for (thirdThrow in DartThrow.allPossibleThrowsSorted) {
                    allPossiblePlays += ThreeThrows(firstThrow, secondThrow, thirdThrow)
                }
            }
        }
    }.filter{it.wasLastDartThrownOnBoardADouble()}
        .groupBy { it.getTotal() }

}


class WinnablePlayCalculatorBruteForceService : WinnablePlayCalculator {
    override fun calculateWinnablePlays(target: Int): Set<Play> {
        val list = lookupMap[target]?.map {
            Play(it.dartOne,it.dartTwo,it.dartThree,target)
        }
        return list?.toSet() ?: emptySet()
    }
}