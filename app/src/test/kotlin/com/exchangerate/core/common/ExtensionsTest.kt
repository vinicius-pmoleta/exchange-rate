package com.exchangerate.core.common

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ExtensionsTest {

    @Test
    fun `validate percentage when total is valid`() {
        assertEquals(20F, 10.percent(50))
    }

    @Test
    fun `validate percentage when total is invalid`() {
        assertEquals(0F, 10.percent(0))
    }

}