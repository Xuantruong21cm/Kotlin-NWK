package com.example.kotlin_networking.core

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

class MainThreadExecutor : Executor {

    private val handler: Handler = Handler(Looper.getMainLooper())

    override fun execute(runnable: Runnable?) {
        handler.post(runnable!!)
    }

}