package com.example.kotlin_networking

import android.content.Context
import com.example.kotlin_networking.common.Method
import com.example.kotlin_networking.internal.KotRequestQueue
import com.example.kotlin_networking.requestbuidler.DownloadBuilder
import com.example.kotlin_networking.requestbuidler.GetRequestBuilder
import com.example.kotlin_networking.requestbuidler.MultipartRequestBuilder
import com.example.kotlin_networking.requestbuidler.PostRequestBuilder

class KotlinNWK private constructor() {


    companion object {

        fun initialize(context: Context) {

        }

        fun get(url: String): GetRequestBuilder {
            return GetRequestBuilder(url)
        }

        fun head(url: String): GetRequestBuilder {
            return GetRequestBuilder(url, Method.HEAD)
        }

        fun post(url: String): PostRequestBuilder {
            return PostRequestBuilder(url)
        }

        fun put(url: String): PostRequestBuilder {
            return PostRequestBuilder(url, Method.PUT)
        }

        fun upload(url: String): MultipartRequestBuilder {
            return MultipartRequestBuilder(url, Method.POST)
        }

        fun download(url: String, dirPath: String, fileName: String): DownloadBuilder {
            return DownloadBuilder(url, dirPath, fileName)
        }

        fun cancelAll() {
            KotRequestQueue.instance.cancelAll(false)
        }
        fun forceCancelAll() {
            KotRequestQueue.instance.cancelAll(true)
        }

        fun cancel(tag: Any) {
            KotRequestQueue.instance.cancelRequestWithGivenTag(tag, false)
        }

    }


}