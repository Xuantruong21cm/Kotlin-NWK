package com.example.kotlin_networking.requestbuidler

import com.example.kotlin_networking.common.Method
import com.example.kotlin_networking.request.KotRequest
import com.example.kotlin_networking.untils.ParseUtil
import java.io.File

class MultipartRequestBuilder(var url: String, var method: Method = Method.POST)
    : RequestBuilderImpl() {

    var mMultiPartParameterMap : MutableMap<String, String> = mutableMapOf()
    var mPercentageThresholdForCancelling : Int = 0
    var mCustomContentType: String? = null
    var mMultiPartFileMap: MutableMap<String, File> = mutableMapOf()



    fun addMultiPartParameter(key: String, value: String): MultipartRequestBuilder {
        mMultiPartParameterMap.put(key, value)
        return this
    }

    fun addMultiPartParameter(objectAny: Any): MultipartRequestBuilder {
        ParseUtil.parserFactory?.getStringMap(objectAny)?.let {
            stringMap -> this.mMultiPartParameterMap.putAll(stringMap)
        }
        return this
    }

    fun addMultiPartParameter(params : MutableMap<String, String>): MultipartRequestBuilder {
        mMultiPartParameterMap.putAll(params)
        return this
    }


    fun addMultiPartFile(key: String, file: File): MultipartRequestBuilder {
        mMultiPartFileMap.put(key, file)
        return this
    }

    fun addMultiPartFile(params: MutableMap<String, File>): MultipartRequestBuilder {
        mMultiPartFileMap.putAll(params)
        return this
    }


    fun setContentType(contentType: String): MultipartRequestBuilder {
        this.mCustomContentType = contentType
        return this
    }

    fun setPercentageThresholdForCancelling(threshold: Int): MultipartRequestBuilder {
        mPercentageThresholdForCancelling = threshold
        return this
    }


    override fun build(): KotRequest {
        return KotRequest(this)
    }
}