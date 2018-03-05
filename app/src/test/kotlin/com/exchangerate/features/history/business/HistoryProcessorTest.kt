package com.exchangerate.features.history.business

import android.arch.paging.DataSource
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.features.history.data.HistoryDao
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class HistoryProcessorTest {

    private val historyDao: HistoryDao = mockk(relaxed = true)

    private val processor = HistoryProcessor(historyDao)

    @Test
    fun `verify data source factory is returned from dao`() {
        val dataSource: DataSource.Factory<Int, HistoryEntity> = mockk(relaxed = true)
        every { historyDao.getAllHistory() } returns dataSource
        val result = processor.loadHistoryOrderByDate()

        result.test()
                .assertValue(dataSource)
    }
}