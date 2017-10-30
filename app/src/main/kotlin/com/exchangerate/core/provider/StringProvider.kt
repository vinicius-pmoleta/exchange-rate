package com.exchangerate.core.provider

import android.content.Context
import android.support.annotation.StringRes
import com.exchangerate.core.di.ForApplication
import javax.inject.Inject

class StringProvider(@Inject @ForApplication val context: Context) {

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

}