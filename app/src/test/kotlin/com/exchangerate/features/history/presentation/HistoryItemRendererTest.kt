package com.exchangerate.features.history.presentation

import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.features.history.presentation.model.HistoryItemScreenModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class HistoryItemRendererTest {

    private val renderer = HistoryItemRenderer()

    @Test
    fun `verify entity converted when valid`() {
        val entity = HistoryEntity(1519898400, "EUR", "USD", 1000F, 2F)
        val model = renderer.convert(entity)
        assertNotNull(model)
        assertEquals(HistoryItemScreenModel("01/03 10:00", "EUR", "USD",
                "1000.000", "2000.000", "2.000"), model)
    }

    @Test
    fun `verify entity not converted when not valid`() {
        val model = renderer.convert(null)
        assertNull(model)
    }
}