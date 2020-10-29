package com.example.kotlin_networking.requestbuidler

import com.example.kotlin_networking.common.Method
import com.example.kotlin_networking.request.KotRequest

class GetRequestBuilder(var url: String, var method: Method = Method.GET) : RequestBuilderImpl() {

    override fun build(): KotRequest {
        return KotRequest(this)
    }
}