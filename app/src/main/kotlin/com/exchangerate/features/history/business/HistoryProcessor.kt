package com.exchangerate.features.history.business

import android.arch.paging.DataSource
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.core.structure.MviProcessor
import com.exchangerate.features.history.data.HistoryDao
import io.reactivex.Observable

class HistoryProcessor(
        private val historyDao: HistoryDao
) : MviProcessor {

    fun loadHistoryOrderByDate(): Observable<DataSource.Factory<Int, HistoryEntity>> {
        return Observable.just(historyDao.getAllHistory())
    }
}