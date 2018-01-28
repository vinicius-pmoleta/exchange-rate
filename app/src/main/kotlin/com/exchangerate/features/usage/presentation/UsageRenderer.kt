package com.exchangerate.features.usage.presentation

import android.util.Log
import android.view.View
import android.widget.Toast
import com.exchangerate.R
import com.exchangerate.core.structure.MviRenderer
import com.exchangerate.features.usage.data.UsageState
import com.exchangerate.features.usage.presentation.model.UsageScreenConverter
import com.exchangerate.features.usage.presentation.model.UsageScreenModel
import kotlinx.android.synthetic.main.usage_fragment.view.*

class UsageRenderer(private val screenConverter: UsageScreenConverter) : MviRenderer<UsageState> {

    override fun render(state: UsageState?, view: View) {
        state?.apply {
            renderLoading(state.isLoading, view)
            state.data?.apply {
                val screenModel = screenConverter.prepareForPresentation(this)
                renderData(screenModel, view)
            }
            state.error?.apply {
                Log.e("UsageRenderer", "Error on usage state renderer", this)
                renderError(view)
            }
        }
    }

    private fun renderLoading(isLoading: Boolean, view: View) {
        view.usageLoadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun renderData(usage: UsageScreenModel, view: View) {
        view.usageStatusView.text = usage.toString()
    }

    private fun renderError(view: View) {
        Toast.makeText(view.context, R.string.default_error_remote_message, Toast.LENGTH_LONG).show()
    }
}