package com.example.kotlin_networking.requestbuidler

import com.example.kotlin_networking.request.KotRequest

class DownloadBuilder(val url: String, val dirPath: String, val fileName: String) : RequestBuilderImpl() {

    var percentageThresholdForCancelling: Int = 0

    fun setPercentageThresholdForCancelling(percentageThresholdForCancelling: Int): DownloadBuilder {
        this.percentageThresholdForCancelling = percentageThresholdForCancelling
        return this
    }

    override fun build(): KotRequest {
        return KotRequest(this)
    }

}