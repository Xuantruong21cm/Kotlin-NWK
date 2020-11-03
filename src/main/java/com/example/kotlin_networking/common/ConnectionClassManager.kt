package com.example.kotlin_networking.common

import com.example.kotlin_networking.core.Core

class ConnectionClassManager private constructor() {

    private object Holder {
        val INSTANCE = ConnectionClassManager()
    }

    companion object {

        private val BYTES_TO_BITS = 8
        private val DEFAULT_SAMPLES_TO_QUALITY_CHANGE = 5
        private val MINIMUM_SAMPLES_TO_DECIDE_QUALITY = 2
        private val DEFAULT_POOR_BANDWIDTH = 150
        private val DEFAULT_MODERATE_BANDWIDTH = 550
        private val DEFAULT_GOOD_BANDWIDTH = 2000
        private val BANDWIDTH_LOWER_BOUND: Long = 10
        val instance: ConnectionClassManager? by lazy { Holder.INSTANCE }

    }

    private var mCurrentConnectionQuality = ConnectionQuality.UNKNOWN
    private var mCurrentBandwidthForSampling = 0
    private var mCurrentNumberOfSample = 0
    private var mCurrentBandwidth = 0
    private var mConnectionQualityCallback: ((connectionQuality: ConnectionQuality,
                                              currentBandWidth: Int) -> Unit)? = null

    @Synchronized fun updateBandwidth(bytes: Long?, timeInMs: Long) {
        if (bytes == null) return

        if (timeInMs == 0L || bytes < 20000
                || bytes * 1.0 / timeInMs * BYTES_TO_BITS < BANDWIDTH_LOWER_BOUND) {
            return
        }

        val bandwidth = bytes * 1.0 / timeInMs * BYTES_TO_BITS

        mCurrentBandwidthForSampling = ((mCurrentBandwidthForSampling * mCurrentNumberOfSample + bandwidth)
                / (mCurrentNumberOfSample + 1)).toInt()
        mCurrentNumberOfSample++

        if (mCurrentNumberOfSample == DEFAULT_SAMPLES_TO_QUALITY_CHANGE
                || mCurrentConnectionQuality === ConnectionQuality.UNKNOWN
                && mCurrentNumberOfSample == MINIMUM_SAMPLES_TO_DECIDE_QUALITY) {

            val lastConnectionQuality = mCurrentConnectionQuality

            mCurrentBandwidth = mCurrentBandwidthForSampling

            mCurrentConnectionQuality = when {
                mCurrentBandwidthForSampling <= 0 -> ConnectionQuality.UNKNOWN
                mCurrentBandwidthForSampling < DEFAULT_POOR_BANDWIDTH -> ConnectionQuality.POOR
                mCurrentBandwidthForSampling < DEFAULT_MODERATE_BANDWIDTH -> ConnectionQuality.MODERATE
                mCurrentBandwidthForSampling < DEFAULT_GOOD_BANDWIDTH -> ConnectionQuality.GOOD
                mCurrentBandwidthForSampling > DEFAULT_GOOD_BANDWIDTH -> ConnectionQuality.EXCELLENT
                else -> ConnectionQuality.UNKNOWN
            }

            if (mCurrentNumberOfSample == DEFAULT_SAMPLES_TO_QUALITY_CHANGE) {
                mCurrentBandwidthForSampling = 0
                mCurrentNumberOfSample = 0
            }
            mConnectionQualityCallback?.let {
                if (mCurrentConnectionQuality !== lastConnectionQuality) {
                    Core.instance.executorSupplier.forMainThreadTasks()
                            .execute {
                                mConnectionQualityCallback?.invoke(mCurrentConnectionQuality, mCurrentBandwidth)
                            }
                }
            }

        }
    }

    fun getCurrentBandWidth(): Int {
        return mCurrentBandwidth
    }

    fun getConnectionQuality(): ConnectionQuality {
        return mCurrentConnectionQuality
    }

    fun setCallback(handler: (connectionQuality: ConnectionQuality, currentBandWidth: Int) -> Unit) {
        mConnectionQualityCallback = handler
    }

    fun removeCallback() {
        mConnectionQualityCallback = null
    }

}