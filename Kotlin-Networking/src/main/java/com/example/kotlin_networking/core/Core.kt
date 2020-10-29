package com.example.kotlin_networking.core

class Core private constructor() {

    val executorSupplier: ExecutorSupplier = DefaultExecutorSupplier()

    private object Holder {
        val INSTANCE = Core()
    }

    companion object {
        val instance: Core by lazy { Holder.INSTANCE }
    }

}