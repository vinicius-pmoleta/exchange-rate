package com.exchangerate.features.history.presentation

import android.arch.paging.PagedList
import android.util.Log
import com.exchangerate.core.structure.MviRenderer
import com.exchangerate.features.history.data.model.HistoryState
import com.exchangerate.features.history.presentation.model.HistoryScreenModel

class HistoryRenderer : MviRenderer<HistoryState, HistoryView> {

    companion object {
        private val HISTORY_LIST_PAGE_SIZE = 20
        private val PREFETCH_DISTANCE = HISTORY_LIST_PAGE_SIZE / 2
        private val HISTORY_LIST_INITIAL_SIZE = 2 * HISTORY_LIST_PAGE_SIZE
    }

    override fun render(state: HistoryState?, view: HistoryView) {
        state?.let {
            val screenModel = HistoryScreenModel(state.isLoading, state.data)
            view.renderData(screenModel, getHistoryPagedListConfiguration())

            it.error?.let {
                Log.e("HistoryRenderer", "Error on usage state renderer", it)
                view.renderError()
            }
        }
    }

    private fun getHistoryPagedListConfiguration() : PagedList.Config {
        return PagedList.Config.Builder()
                .setPageSize(HISTORY_LIST_PAGE_SIZE)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(HISTORY_LIST_INITIAL_SIZE)
                .build()
    }
}