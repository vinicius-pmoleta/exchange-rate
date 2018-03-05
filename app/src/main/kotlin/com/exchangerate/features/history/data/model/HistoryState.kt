package com.exchangerate.features.history.data.model

import android.arch.paging.DataSource
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.core.structure.MviState

data class HistoryState(
        val isLoading: Boolean = false,
        val data: DataSource.Factory<Int, HistoryEntity>? = null,
        val error: Throwable? = null
) : MviState