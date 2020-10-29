package com.example.kotlin_networking.requestbuidler

import com.example.kotlin_networking.common.Method
import com.example.kotlin_networking.request.KotRequest
import com.example.kotlin_networking.untils.ParseUtil
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class PostRequestBuilder(var url: String, var method: Method = Method.POST) : RequestBuilderImpl() {

    var bodyParameterMap: MutableMap<String, String> = mutableMapOf()
    var urlEncodedFormBodyParameterMap: MutableMap<String, String> = mutableMapOf()
    var file: File? = null
    var bytes: ByteArray? = null
    var stringBody: String? = null
    var applicationJsonString: String? = null
    var customContentType: String? = null


    fun addBodyParameter(key: String, value: String): PostRequestBuilder {
        this.bodyParameterMap.put(key, value)
        return this
    }

    fun addBodyParameter(objectAny: Any): PostRequestBuilder {
        ParseUtil.parserFactory?.getStringMap(objectAny)?.let { stringMap -> this.bodyParameterMap.putAll(stringMap) }
        return this
    }

    fun addBodyParamter(params: MutableMap<String, String>): PostRequestBuilder {
        this.bodyParameterMap.putAll(params)
        return this
    }

    fun addUrlEncodedFormBodyParamete(key: String, value: String): PostRequestBuilder {
        this.urlEncodedFormBodyParameterMap.put(key, value)
        return this
    }

    fun addUrlEncodedFormBodyParamete(objectAny: Any): PostRequestBuilder {
        ParseUtil.parserFactory?.getStringMap(objectAny)?.let { stringMap -> this.urlEncodedFormBodyParameterMap.putAll(stringMap) }
        return this
    }

    fun addUrlEncodedFormBodyParamete(params: MutableMap<String, String>): PostRequestBuilder {
        this.urlEncodedFormBodyParameterMap.putAll(params)
        return this
    }

    fun addStringBody(stringBody: String): PostRequestBuilder {
        this.stringBody = stringBody
        return this
    }

    fun addFileBody(file: File): PostRequestBuilder {
        this.file = file
        return this
    }

    fun addByteBody(bytes: ByteArray): PostRequestBuilder {
        this.bytes = bytes
        return this
    }

    fun addApplicationJsonBody(jsonObject: JSONObject): PostRequestBuilder {
        this.applicationJsonString = jsonObject.toString()
        return this
    }

    fun addApplicationJsonBody(jsonArray: JSONArray): PostRequestBuilder {
        this.applicationJsonString = jsonArray.toString()
        return this
    }

    fun addApplicationJsonBody(objectAny: Any): PostRequestBuilder {
        ParseUtil.parserFactory?.getString(objectAny)?.let { string -> this.applicationJsonString = string }
        return this
    }

    fun setContentType(contentType: String): PostRequestBuilder {
        this.customContentType = contentType
        return this
    }

    override fun build(): KotRequest {
        return KotRequest(this)
    }

}