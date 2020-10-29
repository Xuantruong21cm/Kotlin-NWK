package com.example.kotlin_networking.common

import com.example.kotlin_networking.erro.KotError
import okhttp3.Response

class KotResponse<T> {

    companion object {
        fun <T> success(result: T): KotResponse<T> = KotResponse(result)
        fun failed(error: KotError): KotResponse<KotError> = KotResponse(error)
    }

    val result: T?
    val error: KotError?
    lateinit var response: Response

    constructor(result: T) {
        this.result = result
        this.error = null
    }

    constructor(error: KotError) {
        this.result = null
        this.error = error
    }

    fun isSuccess(): Boolean = (error == null)
}