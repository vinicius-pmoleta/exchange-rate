package com.exchangerate.features.history.data.model

import android.arch.paging.DataSource
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.core.structure.MviAction

interface HistoryAction : MviAction

class PrepareToLoadHistoryAction : HistoryAction

class LoadHistoryAction : HistoryAction

data class SuccessfulHistoryResultAction(
        val data: DataSource.Factory<Int, HistoryEntity>
) : HistoryAction

data class FailedHistoryResultAction(
        val error: Throwable
) : HistoryAction