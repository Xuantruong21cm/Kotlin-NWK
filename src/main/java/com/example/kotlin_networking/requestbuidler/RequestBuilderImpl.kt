package com.example.kotlin_networking.requestbuidler

import com.example.kotlin_networking.common.Priority
import com.example.kotlin_networking.common.RequestBuilder
import com.example.kotlin_networking.untils.ParseUtil
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

abstract class RequestBuilderImpl : RequestBuilder {

    var priority: Priority = Priority.MEDIUM
    var tag: Any? = null
    val headersMap: MutableMap<String, String> = mutableMapOf()
    val queryParameterMap: MutableMap<String, String> = mutableMapOf()
    val pathParameterMap: MutableMap<String, String> = mutableMapOf()
    var cacheControl: CacheControl? = null
    var executor: Executor? = null
    var okHttpClient: OkHttpClient? = null
    var userAgent: String? = null


    override fun setPriority(priority: Priority): RequestBuilder {
        this.priority = priority
        return this
    }

    override fun setTag(tag: Any): RequestBuilder {
        this.tag = tag
        return this
    }

    override fun addHeaders(key: String, value: String): RequestBuilder {
        headersMap.put(key, value)
        return this
    }

    override fun addHeaders(headerMap: MutableMap<String, String>): RequestBuilder {
        headersMap.putAll(headerMap)
        return this
    }

    override fun addHeaders(objectAny: Any): RequestBuilder {
        ParseUtil.parserFactory?.getStringMap(objectAny)?.let { it -> headersMap.putAll(it) }
        return this
    }

    override fun addQueryParameter(key: String, value: String): RequestBuilder {
        queryParameterMap.put(key, value)
        return this
    }

    override fun addQueryParameter(queryParameterMap: MutableMap<String, String>): RequestBuilder {
        this.queryParameterMap.putAll(queryParameterMap)
        return this
    }

    override fun addQueryParameter(objectAny: Any): RequestBuilder {
        ParseUtil.parserFactory?.getStringMap(objectAny)?.let { it -> queryParameterMap.putAll(it) }
        return this
    }

    override fun addPathParameter(key: String, value: String): RequestBuilder {
        pathParameterMap.put(key, value)
        return this
    }

    override fun addPathParameter(pathParameterMap: MutableMap<String, String>): RequestBuilder {
        this.pathParameterMap.putAll(pathParameterMap)
        return this
    }

    override fun addPathParameter(objectAny: Any): RequestBuilder {
        ParseUtil.parserFactory?.getStringMap(objectAny)?.let { it -> pathParameterMap.putAll(it) }
        return this
    }

    override fun doNotCacheResponse(): RequestBuilder {
        cacheControl = CacheControl.Builder().noStore().build()
        return this
    }

    override fun getResponseOnlyIfCached(): RequestBuilder {
        cacheControl = CacheControl.FORCE_CACHE
        return this
    }

    override fun getResponseOnlyFromNetwork(): RequestBuilder {
        cacheControl = CacheControl.FORCE_NETWORK
        return this
    }

    override fun setMaxAgeCacheControl(maxAge: Int, timeUnit: TimeUnit): RequestBuilder {
        cacheControl = CacheControl.Builder().maxAge(maxAge, timeUnit).build()
        return this
    }

    override fun setMaxStaleCacheControl(maxStale: Int, timeUnit: TimeUnit): RequestBuilder {
        cacheControl = CacheControl.Builder().maxStale(maxStale, timeUnit).build()
        return this
    }

    override fun setExecutor(executor: Executor): RequestBuilder {
        this.executor = executor
        return this
    }

    override fun setOkHttpClient(okHttpClient: OkHttpClient): RequestBuilder {
        this.okHttpClient = okHttpClient
        return this
    }

    override fun setUserAgent(userAgent: String): RequestBuilder {
        this.userAgent = userAgent
        return this
    }
}