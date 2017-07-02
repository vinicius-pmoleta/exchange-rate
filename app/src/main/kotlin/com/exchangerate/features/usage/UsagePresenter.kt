package com.exchangerate.features.usage

import com.exchangerate.core.data.usecase.FetchUsageUseCase

class UsagePresenter(val view: UsageContract.View, val fetchUsageUseCase: FetchUsageUseCase) : UsageContract.Action {

}