package com.exchangerate.core.common

fun Int.percent(total: Int): Float {
    if (total == 0) {
        return 0f
    }
    return 100 * (this.toFloat() / total.toFloat())
}