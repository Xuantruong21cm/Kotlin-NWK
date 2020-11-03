package com.example.kotlin_networking.untils

import com.example.kotlin_networking.gsonparserfactory.GsonParserFactory
import com.example.kotlin_networking.interfaces.Parser

class ParseUtil {
    companion object {
        var parserFactory: Parser.Factory? = GsonParserFactory()
        fun shutDown() {
            parserFactory = null
        }
    }
}
