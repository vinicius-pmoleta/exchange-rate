package com.exchangerate.features.history.data.model

import android.arch.paging.PagedList
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.core.structure.MviState

data class HistoryState(
        val isLoading: Boolean = false,
        val data: PagedList<HistoryEntity>? = null,
        val error: Throwable? = null
) : MviState