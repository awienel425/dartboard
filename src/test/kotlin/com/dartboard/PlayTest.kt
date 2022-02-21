package com.dartboard


import com.dartboard.DartThrow.Companion.MISSED_OR_DECLINED_THROW
import com.dartboard.WinnablePlayCalculatorFlatMapService.Companion.addAllWinnableSecondThrows
import com.dartboard.WinnablePlayCalculatorFlatMapService.Companion.addAllWinnableThirdThrows
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

internal class PlayTest {

    @Test
    fun `should Detect Play With Last Throw As Double If Only One Dart Was Thrown`() {
        assertThat(Play(DartThrow(1, BlockType.Single), target = 1).wasLastDartThrownOnBoardADouble(), equalTo(false))
        assertThat(Play(DartThrow(1, BlockType.Triple), target = 1).wasLastDartThrownOnBoardADouble(), equalTo(false))
        assertThat(Play(DartThrow(1, BlockType.Double), target = 1).wasLastDartThrownOnBoardADouble(), equalTo(true))
    }

    @Test
    fun `should Detect Play With Last Throw As Double If Only Two Darts Were Thrown`() {
        assertThat(
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                secondThrow = DartThrow(1, BlockType.Double),
                target = 1
            ).wasLastDartThrownOnBoardADouble(), equalTo(true)
        )

        assertThat(
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                secondThrow = DartThrow(1, BlockType.Triple),
                target = 1
            ).wasLastDartThrownOnBoardADouble(), equalTo(false)
        )

        assertThat(
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                secondThrow = DartThrow(1, BlockType.Single),
                target = 1
            ).wasLastDartThrownOnBoardADouble(), equalTo(false)
        )
    }

    @Test
    fun `should Detect Play With Last Throw As Double If Three Darts Were Thrown`() {

        assertThat(
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                secondThrow = DartThrow(1, BlockType.Double),
                thirdThrow = DartThrow(1, BlockType.Single),
                target = 1
            ).wasLastDartThrownOnBoardADouble(), equalTo(false)
        )

        assertThat(
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                secondThrow = DartThrow(1, BlockType.Double),
                thirdThrow = DartThrow(1, BlockType.Single),
                target = 1
            ).wasLastDartThrownOnBoardADouble(), equalTo(false)
        )
    }

    @Test
    fun `should Detect winnable first throw`() {
        assertThat(MISSED_OR_DECLINED_THROW.isValidFirstThrow(0), equalTo(true))
        assertThat(DartThrow(1).isValidFirstThrow(1), equalTo(true))
        assertThat(MISSED_OR_DECLINED_THROW.isValidFirstThrow(1), equalTo(true))
        assertThat(DartThrow(2).isValidFirstThrow(1), equalTo(false))
    }

    @Test
    fun `should Detect winnable second throws`() {
        val allWinnableSecondThrows = addAllWinnableSecondThrows(
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                target = 3
            )
        ).map { it.secondThrow }.toSet()
        assertThat(
            allWinnableSecondThrows, equalTo(
                setOf(
                    DartThrow(0, BlockType.Single),
                    DartThrow(1, BlockType.Single),
                    DartThrow(1, BlockType.Double),
                    DartThrow(1, BlockType.Triple),
                    DartThrow(2, BlockType.Single),
                    DartThrow(3, BlockType.Single)
                )
            )
        )
    }

    @Test
    fun `should Detect winnable third throws where target is reached on second throw`() {
        val allWinnableThirdThrows = addAllWinnableThirdThrows(
            Play(
                firstThrow = DartThrow(1),
                secondThrow = DartThrow(1, BlockType.Double),
                target = 3
            )
        )
            .map { it.thirdThrow }.toSet()
        assertThat(
            allWinnableThirdThrows, equalTo(
                setOf(
                    MISSED_OR_DECLINED_THROW
                )
            )
        )
    }

    @Test
    fun `should Detect winnable third throws where target is reached on first throw`() {
        val allWinnableThirdThrows = addAllWinnableThirdThrows(
            Play(
                firstThrow = DartThrow(1, BlockType.Double),
                target = 2
            )
        )
            .map { it.thirdThrow }.toSet()
        assertThat(
            allWinnableThirdThrows, equalTo(
                setOf(
                    MISSED_OR_DECLINED_THROW
                )
            )
        )
    }


    @Test
    fun `should Detect winnable third throws where target is reached on third throw`() {
        val allWinnableThirdThrows = addAllWinnableThirdThrows(
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                secondThrow = DartThrow(1),
                target = 3
            )
        )
            .map { it.thirdThrow }.toSet()
        assertThat(
            allWinnableThirdThrows, equalTo(
                setOf(
                    DartThrow(1, BlockType.Double),
                )
            )
        )
    }

    @Test
    fun `should detect winnable first throw`() {

        assertThat(MISSED_OR_DECLINED_THROW.isValidFirstThrow(2), equalTo(true))
        assertThat(DartThrow(1).isValidFirstThrow(2), equalTo(true))
        assertThat(DartThrow(2).isValidFirstThrow(2), equalTo(true))
        assertThat(DartThrow(3).isValidFirstThrow(2), equalTo(false))
    }

    @Test
    fun `should detect winnable second throw`() {
        Play(MISSED_OR_DECLINED_THROW, target = 3).also { play ->
            assertThat(play.isValidSecondThrow(MISSED_OR_DECLINED_THROW), equalTo(true))
            assertThat(play.isValidSecondThrow(DartThrow(1)), equalTo(true))
            assertThat(play.isValidSecondThrow(DartThrow(1, BlockType.Double)), equalTo(true))
            assertThat(play.isValidSecondThrow(DartThrow(1, BlockType.Triple)), equalTo(true))
            assertThat(play.isValidSecondThrow(DartThrow(2)), equalTo(true))
            assertThat(play.isValidSecondThrow(DartThrow(2, BlockType.Double)), equalTo(false))
            assertThat(play.isValidSecondThrow(DartThrow(3)), equalTo(true))
            assertThat(play.isValidSecondThrow(DartThrow(3, BlockType.Double)), equalTo(false))
            assertThat(play.isValidSecondThrow(DartThrow(3, BlockType.Triple)), equalTo(false))
            assertThat(play.isValidSecondThrow(DartThrow(4)), equalTo(false))
        }
    }

    @Test
    fun `should detect winnable third throw`() {
        Play(
            firstThrow = MISSED_OR_DECLINED_THROW,
            secondThrow = MISSED_OR_DECLINED_THROW,
            target = 4
        ).also { play ->
            assertThat(play.isValidThirdThrow(DartThrow(2, BlockType.Double)), equalTo(true))
            assertThat(
                "total score == target but not a double",
                play.isValidThirdThrow(DartThrow(4, BlockType.Single)),
                equalTo(false)
            )
            assertThat(
                "total score != target and not a double",
                play.isValidThirdThrow(MISSED_OR_DECLINED_THROW),
                equalTo(false)
            )
            assertThat(
                "a double but total score != target ",
                play.isValidThirdThrow(DartThrow(3, BlockType.Double)),
                equalTo(false)
            )
        }
    }

    @Test
    fun `should detect winnable third throw, where third throw is declined`() {
        assertThat(
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                secondThrow = DartThrow(2, BlockType.Double),
                target = 4
            ).isValidThirdThrow(MISSED_OR_DECLINED_THROW), equalTo(true)
        )

        assertThat(
            "The last throw that hit the board needed to be a double",
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                secondThrow = DartThrow(4, BlockType.Single),
                target = 4
            ).isValidThirdThrow(MISSED_OR_DECLINED_THROW), equalTo(false)
        )

        assertThat(
            Play(
                firstThrow = DartThrow(2, BlockType.Double),
                secondThrow = MISSED_OR_DECLINED_THROW,
                target = 4
            ).isValidThirdThrow(MISSED_OR_DECLINED_THROW), equalTo(true)
        )
    }

    @Test
    fun `should detect when last throw on board was a double`() {
        assertThat(
            Play(
                firstThrow = DartThrow(1, BlockType.Single),
                secondThrow = MISSED_OR_DECLINED_THROW,
                thirdThrow = MISSED_OR_DECLINED_THROW,
                target = 4
            )
                .wasLastDartThrownOnBoardADouble(),
            equalTo(false)
        )

        assertThat(
            Play(
                firstThrow = DartThrow(2, BlockType.Double),
                secondThrow = MISSED_OR_DECLINED_THROW,
                thirdThrow = MISSED_OR_DECLINED_THROW,
                target = 4
            )
                .wasLastDartThrownOnBoardADouble(),
            equalTo(true)
        )

        assertThat(
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                secondThrow = DartThrow(2, BlockType.Double),
                thirdThrow = MISSED_OR_DECLINED_THROW,
                target = 4
            )
                .wasLastDartThrownOnBoardADouble(),
            equalTo(true)
        )

        assertThat(
            Play(
                firstThrow = MISSED_OR_DECLINED_THROW,
                secondThrow = MISSED_OR_DECLINED_THROW,
                thirdThrow = DartThrow(2, BlockType.Double),
                target = 4
            )
                .wasLastDartThrownOnBoardADouble(),
            equalTo(true)
        )

    }

    @Test
    fun `should calculate play total`() {
        val dontCare = 200
        val play = Play(
            firstThrow = DartThrow(13, BlockType.Triple),
            DartThrow(20, BlockType.Triple),
            DartThrow(20, BlockType.Double), target = dontCare
        )
        assertThat(play.getTotal(), equalTo(139))


    }


}