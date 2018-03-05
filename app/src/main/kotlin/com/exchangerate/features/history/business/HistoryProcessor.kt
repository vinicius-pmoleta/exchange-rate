package com.exchangerate.features.history.business

import android.arch.paging.PagedList
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.core.structure.MviProcessor
import com.exchangerate.features.history.data.HistoryDao
import io.reactivex.Observable
import java.util.concurrent.Executor

class HistoryProcessor(
        private val historyDao: HistoryDao,
        private val backgroundExecutor: Executor,
        private val mainExecutor: Executor
) : MviProcessor {

    fun loadHistoryOrderByDate(): Observable<PagedList<HistoryEntity>> {
        return Observable.just(historyDao.getAllHistory())
                .map { dataSource ->
                    val configuration = PagedList.Config.Builder()
                            .setPageSize(20)
                            .setPrefetchDistance(10)
                            .setEnablePlaceholders(true)
                            .setInitialLoadSizeHint(40)
                            .build()
                    PagedList.Builder<Int, HistoryEntity>(dataSource.create(), configuration)
                            .setMainThreadExecutor(mainExecutor)
                            .setBackgroundThreadExecutor(backgroundExecutor)
                            .build()
                }
    }

}