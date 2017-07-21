package com.exchangerate.core.data.repository.local

import android.content.Context
import okhttp3.Cache
import java.io.File

class NetworkCache(val context: Context) {

    fun initialise(): Cache? {
        return Cache(File(context.cacheDir, "network-cache"), 10 * 1024 * 1024)
    }

}