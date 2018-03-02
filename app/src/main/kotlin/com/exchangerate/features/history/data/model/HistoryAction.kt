package com.exchangerate.features.history.data.model

import android.arch.paging.PagedList
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.core.structure.MviAction

sealed class HistoryAction : MviAction

class PrepareToLoadHistoryAction : HistoryAction()

class LoadHistoryAction : HistoryAction()

data class SuccessfulHistoryResultAction(
        val data: PagedList<HistoryEntity>
) : HistoryAction()

data class FailedHistoryResultAction(
        val error: Throwable
) : HistoryAction()