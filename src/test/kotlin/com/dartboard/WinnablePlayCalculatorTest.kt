package com.dartboard

import com.dartboard.DartThrow.Companion.MISSED_OR_DECLINED_THROW
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class WinnablePlayCalculatorTest {
    private val calculationServices: Set<WinnablePlayCalculator> =
        setOf(
            WinnablePlayCalculatorFlatMapService(),
            WinnablePlayCalculatorBruteForceService()
        )


    @Test
    fun `should calculate that there is no possible plays for target of One`() {
        val target = 1
        calculationServices.forEach { playCalculator ->
            val winnablePlays = playCalculator.calculateWinnablePlays(target)
            assertThat(
                winnablePlays, equalTo(emptySet())
            )
        }
    }

    @Test
    fun `should calculate that there is no possible plays for target of 181`() {
        val target = 181
        calculationServices.forEach { playCalculator ->
            val winnablePlays = playCalculator.calculateWinnablePlays(target)
            assertThat(
                winnablePlays, equalTo(emptySet())
            )
        }
    }

    @Test
    fun `should calculate that there are many possible plays for the maximum possible target of 160`() {
        val target = 20 * 3 + 20 * 3 + 20 * 2
        calculationServices.forEach { playCalculator ->
            val winnablePlays = playCalculator.calculateWinnablePlays(target)
            assertThat(winnablePlays.isNotEmpty(), equalTo(true))
        }
    }

    @Test
    fun `should calculate that there are no possible plays for 159`() {
        val target = 159
        calculationServices.forEach { playCalculator ->
            val winnablePlays = playCalculator.calculateWinnablePlays(target)
            assertThat(winnablePlays, equalTo(setOf()))
        }
    }

    @Test
    fun `should calculate all possible plays for target of Two`() {
        val target = 2
        calculationServices.forEach { playCalculator ->
            val winnablePlays = playCalculator.calculateWinnablePlays(target)
            assertThat(
                winnablePlays, equalTo(
                    setOf(
                        Play(
                            firstThrow = DartThrow(1, BlockType.Double),
                            secondThrow = MISSED_OR_DECLINED_THROW,
                            thirdThrow = MISSED_OR_DECLINED_THROW,
                            target = target
                        ),
                        Play(
                            firstThrow = MISSED_OR_DECLINED_THROW,
                            secondThrow = DartThrow(1, BlockType.Double),
                            thirdThrow = MISSED_OR_DECLINED_THROW,
                            target = target
                        ),
                        Play(
                            firstThrow = MISSED_OR_DECLINED_THROW,
                            secondThrow = MISSED_OR_DECLINED_THROW,
                            thirdThrow = DartThrow(1, BlockType.Double),
                            target = target
                        )
                    )
                )
            )
        }
    }
}