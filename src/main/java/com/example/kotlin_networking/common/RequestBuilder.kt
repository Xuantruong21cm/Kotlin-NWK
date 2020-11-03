package com.example.kotlin_networking.common

import com.example.kotlin_networking.request.KotRequest
import okhttp3.OkHttpClient
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

interface RequestBuilder {

    fun setPriority(priority: Priority): RequestBuilder

    fun setTag(tag: Any): RequestBuilder

    fun addHeaders(key: String, value: String): RequestBuilder

    fun addHeaders(headerMap: MutableMap<String, String>): RequestBuilder

    fun addHeaders(objectAny: Any): RequestBuilder

    fun addQueryParameter(key: String, value: String): RequestBuilder

    fun addQueryParameter(queryParameterMap: MutableMap<String, String>): RequestBuilder

    fun addQueryParameter(objectAny: Any): RequestBuilder

    fun addPathParameter(key: String, value: String): RequestBuilder

    fun addPathParameter(pathParameterMap: MutableMap<String, String>): RequestBuilder

    fun addPathParameter(objectAny: Any): RequestBuilder

    fun doNotCacheResponse(): RequestBuilder

    fun getResponseOnlyIfCached(): RequestBuilder

    fun getResponseOnlyFromNetwork(): RequestBuilder

    fun setMaxAgeCacheControl(maxAge: Int, timeUnit: TimeUnit): RequestBuilder

    fun setMaxStaleCacheControl(maxStale: Int, timeUnit: TimeUnit): RequestBuilder

    fun setExecutor(executor: Executor): RequestBuilder

    fun setOkHttpClient(okHttpClient: OkHttpClient): RequestBuilder

    fun setUserAgent(userAgent: String): RequestBuilder

    fun build(): KotRequest

}