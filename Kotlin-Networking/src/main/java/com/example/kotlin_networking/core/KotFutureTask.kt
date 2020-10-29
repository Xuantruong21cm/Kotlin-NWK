package com.example.kotlin_networking.core

import com.example.kotlin_networking.common.Priority
import com.example.kotlin_networking.internal.KotRunnable
import java.util.concurrent.FutureTask

class KotFutureTask(var runnable: KotRunnable) : FutureTask<KotRunnable>(runnable, null), Comparable<KotFutureTask> {

    override fun compareTo(other: KotFutureTask): Int {
        val p1: Priority = runnable.priority
        val p2: Priority = other.runnable.priority
        return if (p1 == p2) runnable.sequence - other.runnable.sequence else p2.ordinal - p1.ordinal
    }

}