package com.example.kotlin_networking.core

import com.example.kotlin_networking.internal.KotRunnable
import java.util.concurrent.*

class KotExecutor(maxNumThreads: Int, threadFactory: ThreadFactory) :
        ThreadPoolExecutor(maxNumThreads, maxNumThreads, 0, TimeUnit.MILLISECONDS,
                PriorityBlockingQueue<Runnable>(), threadFactory) {

    override fun submit(task: Runnable?): Future<*> {
        val futureTask = KotFutureTask(task as KotRunnable)
        execute(futureTask)
        return futureTask
    }

}