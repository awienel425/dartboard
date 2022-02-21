package com.citi.interview

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

class DarthThrowTest {

    @Test
    fun `should calculate dart total`(){
        assertThat(DartThrow(13,BlockType.Triple).getScore(), equalTo(39))
        assertThat(DartThrow(20,BlockType.Triple).getScore(), equalTo(60))
        assertThat(DartThrow(20,BlockType.Double).getScore(), equalTo(40))
    }
}