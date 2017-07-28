package com.exchangerate.features.usage.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.exchangerate.features.usage.data.Usage

class UsageDataHolder : ViewModel() {

    var data: LiveData<Usage>? = null

}