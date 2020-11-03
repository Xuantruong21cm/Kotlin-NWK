package com.example.kotlin_networking.erro

import okhttp3.Response

class KotError : Exception {

    var errorBody: String? = null
    var errorCode = 0
    var errorDetail: String? = null
    var response: Response? = null
        private set

    constructor()

    constructor(response: Response) {
        this.response = response
    }

    constructor(message: String) : super(message)

    constructor(message: String, response: Response) : super(message) {
        this.response = response
    }

    constructor(message: String, throwable: Throwable) : super(message, throwable)

    constructor(message: String, response: Response, throwable: Throwable) : super(message, throwable) {
        this.response = response
    }

    constructor(response: Response, throwable: Throwable) : super(throwable) {
        this.response = response
    }

    constructor(throwable: Throwable) : super(throwable)

    fun setCancellationMessageInError() {

    }

    fun <T> getErrorAsObject(objectClass: Class<T>): T? {
        TODO("Add Logic")
    }

}