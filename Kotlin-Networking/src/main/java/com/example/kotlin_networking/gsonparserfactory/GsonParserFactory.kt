package com.example.kotlin_networking.gsonparserfactory

import com.example.kotlin_networking.interfaces.Parser
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.HashMap

class GsonParserFactory(private val gson: Gson = Gson()) : Parser.Factory() {

    override fun responseBodyParser(type: Type): GsonResponseBodyParser<Any?> {
        val typeAdapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return GsonResponseBodyParser(gson, typeAdapter)
    }

    override fun requestBodyParser(type: Type): GsonRequestBodyParser<Any?> {
        val typeAdapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyParser(gson, typeAdapter) as GsonRequestBodyParser<Any?>
    }

    override fun getObject(string: String, type: Type): Any? {
        try {
            return gson.fromJson(string, type)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    override fun getString(objectAny: Any): String? {
        return gson.toJson(objectAny)
    }

    override fun getStringMap(objectAny: Any): HashMap<String, String>? {
        try {
            val type = object : TypeToken<HashMap<String, String>>() {}.type
            return gson.fromJson<HashMap<String, String>>(gson.toJson(objectAny), type)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return HashMap()
    }
}