package com.exchangerate.features.usage.business

import com.exchangerate.features.usage.data.LoadUsageAction
import com.exchangerate.features.usage.data.StartLoadingUsageAction
import com.exchangerate.features.usage.presentation.LoadUsageIntent
import com.exchangerate.features.usage.presentation.UsageIntent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UsageInterpreterTest {

    private val interpreter = UsageInterpreter()

    @Test
    fun `assert that unknown intent does not generate actions`() {
        val unknownIntent = object : UsageIntent {}
        val actions = interpreter.translate(unknownIntent)
        assertTrue(actions.isEmpty())
    }

    @Test
    fun `assert that intent to load usage data results in loading and fetch actions`() {
        val actions = interpreter.translate(LoadUsageIntent())
        assertEquals(2, actions.size)
        assertTrue(actions[0] is StartLoadingUsageAction)
        assertTrue(actions[1] is LoadUsageAction)
    }
}