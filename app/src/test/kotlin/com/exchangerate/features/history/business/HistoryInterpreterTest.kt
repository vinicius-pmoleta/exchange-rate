package com.exchangerate.features.history.business

import com.exchangerate.features.history.data.model.LoadHistoryAction
import com.exchangerate.features.history.data.model.PrepareToLoadHistoryAction
import com.exchangerate.features.history.presentation.model.HistoryInitialIntent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HistoryInterpreterTest {

    private val interpreter = HistoryInterpreter()

    @Test
    fun `assert that initial intent results in loading and fetch actions`() {
        val actions = interpreter.translate(HistoryInitialIntent())
        assertEquals(2, actions.size)
        assertTrue(actions[0] is PrepareToLoadHistoryAction)
        assertTrue(actions[1] is LoadHistoryAction)
    }
}