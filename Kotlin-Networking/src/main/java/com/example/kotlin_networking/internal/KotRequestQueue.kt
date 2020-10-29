package com.example.kotlin_networking.internal

import com.example.kotlin_networking.common.Priority
import com.example.kotlin_networking.core.Core
import com.example.kotlin_networking.request.KotRequest
import org.jetbrains.annotations.NotNull
import java.util.concurrent.atomic.AtomicInteger

class KotRequestQueue private constructor() {

    val sequenceGenerator: AtomicInteger = AtomicInteger()
    var currentRequest: MutableSet<KotRequest> = mutableSetOf()

    private object Holder {
        val INSTANCE = KotRequestQueue()
    }

    companion object {
        val instance: KotRequestQueue by lazy { Holder.INSTANCE }
    }

    fun addRequest(request: KotRequest) {
        synchronized(currentRequest) {
            try {
                currentRequest.add(request)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        try {
            request.sequenceNumber = getSequenceNumber()

            request.future = when (request.priority) {

                Priority.IMMEDIATE -> {
                    Core.instance
                            .executorSupplier
                            .forImmediateNetworkTasks()
                            .submit(KotRunnable(request))
                }

                else -> {
                    Core.instance
                            .executorSupplier
                            .forNetworkTasks()
                            .submit(KotRunnable(request))
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun getSequenceNumber(): Int = sequenceGenerator.incrementAndGet()


    fun cancelRequestWithGivenTag(@NotNull tag: Any, forceCancel: Boolean) {
        synchronized(currentRequest) {
            cancel(object : RequestFilter {
                override fun apply(request: KotRequest): Boolean {
                    if (tag is String && request.tag is String) {
                        return tag == request.tag
                    } else {
                        return tag === request.tag
                    }
                }
            }, forceCancel)
        }
    }

    fun cancel(requestFilter: RequestFilter, forceCancel: Boolean) {
        synchronized(currentRequest) {
            val iterator: MutableIterator<KotRequest> = currentRequest.iterator()
            while (iterator.hasNext()) {
                with(iterator.next() /*KotRequest*/) {
                    if (requestFilter.apply(this /*KotRequest*/)) {
                        cancel(forceCancel)
                        if (isCancelled) {
                            destroy()
                            iterator.remove()
                        }
                    }
                }
            }
        }
    }

    fun cancelAll(forceCancel: Boolean) {
        synchronized(currentRequest) {
            val iterator: MutableIterator<KotRequest> = currentRequest.iterator()
            while (iterator.hasNext()) {
                with(iterator.next() /*KotRequest*/) {
                    cancel(forceCancel)
                    if (isCancelled) {
                        destroy()
                        iterator.remove()
                    }
                }
            }
        }
    }

    fun finish(kotRequest: KotRequest) {
        synchronized(currentRequest) {
            currentRequest.remove(kotRequest)
        }
    }


    interface RequestFilter {
        fun apply(request: KotRequest): Boolean
    }
}