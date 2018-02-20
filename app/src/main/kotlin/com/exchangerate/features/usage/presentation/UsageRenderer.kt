package com.exchangerate.features.usage.presentation

import android.util.Log
import com.exchangerate.core.common.percent
import com.exchangerate.core.data.repository.remote.data.Usage
import com.exchangerate.core.structure.MviRenderer
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.model.UsageScreenModel

class UsageRenderer : MviRenderer<UsageState, UsageView> {

    override fun render(state: UsageState?, view: UsageView) {
        state?.apply {
            val screenModel = UsageScreenModel(
                    state.isLoading,
                    state.data?.averagePerDay.toString(),
                    prepareUsedPercentageToPresent(state.data),
                    state.data?.remaining.toString()
            )
            view.renderData(screenModel)

            state.error?.let {
                Log.e("UsageRenderer", "Error on usage state renderer", it)
                view.renderError()
            }
        }
    }

    private fun prepareUsedPercentageToPresent(data: Usage?): String {
        return data?.let {
            "%.2f".format(it.sent.percent(it.quota))
        } ?: ""
    }
}