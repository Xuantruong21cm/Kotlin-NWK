package com.example.kotlin_networking.gsonparserfactory

import com.example.kotlin_networking.interfaces.Parser
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import java.io.IOException

class GsonResponseBodyParser<out T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Parser<ResponseBody, T> {


    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val jsonReader = gson.newJsonReader(value.charStream())
        value.use {
            return adapter.read(jsonReader)
        }
    }
}