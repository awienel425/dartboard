package com.citi.interview

/**
 * Blocktype represents the the area on the number that was hit with the dart.
 * A number (slice) on the board has a double blocktype (around the outer edge)
 * and a triple (around the centre of the number slice) These block types are used to multiply to get
 * the score for that throw
 */
enum class BlockType(val multiplier: Int) { Single(1), Double(2), Triple(3) }

/**
 * Represents a throw on the board, it has a number a BlockType.
 * Note that a miss or a dart not throw is represented by MISSED_OR_DECLINED_THROW = DartThrow(0, BlockType.Single)
 */
data class DartThrow(val numberOnBoard: Int, val blockType: BlockType = BlockType.Single) {
    companion object {
        val MISSED_OR_DECLINED_THROW = DartThrow(0, BlockType.Single)
        val allPossibleThrowsSorted by lazy {generateAllPossibleThrows(20).sortedBy { it.getScore() }}
        private fun generateAllPossibleThrows(maxBlockNumber:Int): Set<DartThrow> {

            return setOf(MISSED_OR_DECLINED_THROW)
                .union(
                    (1..maxBlockNumber).flatMap { numberOnBoard ->
                        BlockType.values().map { blockType ->
                            DartThrow(numberOnBoard, blockType)
                        }
                    })

        }
    }

    fun isValidFirstThrow(target: Int): Boolean {
        return (this.getScore() <= target)
    }

    override fun toString(): String = "\n[$numberOnBoard,$blockType]"

    /**
     * To get the score for the throw, we multiple the numberOnBoard with the throwType
     */
    fun getScore(): Int {
        return numberOnBoard * blockType.multiplier
    }
}

/**
 * A player only get one play, consisting of 3 darts to be thrown at the board
 */

data class Play(val firstThrow: DartThrow,
                var secondThrow: DartThrow = DartThrow.MISSED_OR_DECLINED_THROW,
                var thirdThrow: DartThrow = DartThrow.MISSED_OR_DECLINED_THROW,
                val target:Int ): ThreeThrows(firstThrow,secondThrow,thirdThrow) {



    private fun getRemainder(): Int {
        return target - getTotal()
    }

    fun isValidSecondThrow(secondDartThrow: DartThrow): Boolean {
        return (secondDartThrow.getScore() <= getRemainder())
    }
    fun isValidSecondThrow(): Boolean {
        return isValidSecondThrow(dartTwo)
    }

    fun isValidThirdThrow(thirdDartThrow: DartThrow): Boolean {
        return (thirdDartThrow.getScore() == getRemainder())
                && (thirdDartThrow.blockType == BlockType.Double
                || (thirdDartThrow == DartThrow.MISSED_OR_DECLINED_THROW && wasLastDartThrownOnBoardADouble()))
    }

    fun isValidThirdThrow(): Boolean {
        return isValidThirdThrow(dartThree)
    }

    override fun toString(): String = "\n[$dartOne,$dartTwo,$dartThree] target=$target, totalScore=${getTotal()}"



}

open class ThreeThrows(
    val dartOne: DartThrow,
    var dartTwo: DartThrow = DartThrow.MISSED_OR_DECLINED_THROW,
    var dartThree: DartThrow = DartThrow.MISSED_OR_DECLINED_THROW,
) {
    internal fun getTotal(): Int {
        return (dartOne.getScore() + dartTwo.getScore() + dartThree.getScore())
    }

    internal fun wasLastDartThrownOnBoardADouble(): Boolean = when {
        this.dartThree != DartThrow.MISSED_OR_DECLINED_THROW -> {
            dartThree.blockType == BlockType.Double
        }
        this.dartTwo != DartThrow.MISSED_OR_DECLINED_THROW -> {
            dartTwo.blockType == BlockType.Double
        }
        this.dartOne != DartThrow.MISSED_OR_DECLINED_THROW -> {
            dartOne.blockType == BlockType.Double
        }
        else -> false
    }
}




