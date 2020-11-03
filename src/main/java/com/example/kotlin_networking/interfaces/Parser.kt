package com.example.kotlin_networking.interfaces

import com.example.kotlin_networking.gsonparserfactory.GsonRequestBodyParser
import com.example.kotlin_networking.gsonparserfactory.GsonResponseBodyParser
import java.io.IOException
import java.lang.reflect.Type

interface Parser<in F, out T> {

    @Throws(IOException::class)
    fun convert(value: F): T

    abstract class Factory {

        open fun responseBodyParser(type: Type): GsonResponseBodyParser<Any?>? {
            return null
        }

        open fun requestBodyParser(type: Type): GsonRequestBodyParser<Any?>? {
            return null
        }

        open fun getObject(string: String, type: Type): Any? {
            return null
        }

        open fun getString(objectAny: Any): String? {
            return null
        }

        open fun getStringMap(objectAny: Any): Map<String, String>? {
            return null
        }

    }
}