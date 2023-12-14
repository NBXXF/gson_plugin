package com.xxf.json.gson.booster.kaptdemo.ui.utils

import android.os.SystemClock


/**
 * 计算耗时
 * @param block
 * @return 耗时 单位纳秒
 */
inline fun runCosting(block: () -> Unit): Long {
    val start = SystemClock.elapsedRealtimeNanos()
    block()
    val end = SystemClock.elapsedRealtimeNanos()
    return end - start
}
