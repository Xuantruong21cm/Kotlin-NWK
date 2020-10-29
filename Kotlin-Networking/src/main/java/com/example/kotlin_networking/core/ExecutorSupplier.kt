package com.example.kotlin_networking.core

import java.util.concurrent.Executor

interface ExecutorSupplier {

    fun forNetworkTasks(): KotExecutor
    fun forImmediateNetworkTasks(): KotExecutor
    fun forMainThreadTasks(): Executor

}