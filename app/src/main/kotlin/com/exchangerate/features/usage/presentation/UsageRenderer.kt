package com.exchangerate.features.usage.presentation

import android.util.Log
import com.exchangerate.core.structure.MviRenderer
import com.exchangerate.features.usage.data.UsageState

class UsageRenderer : MviRenderer<UsageState> {

    override fun render(state: UsageState) {
        Log.d(UsageFragment.TAG, "State changed " + state)
    }
}