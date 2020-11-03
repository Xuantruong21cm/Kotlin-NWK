package com.example.kotlin_networking.untils

import com.example.kotlin_networking.common.ResponseType
import com.example.kotlin_networking.request.KotRequest
import okhttp3.Response

class SourceCloseUtil private constructor() {

    companion object {

        fun close(response: Response?, kotRequest: KotRequest) {

            if (kotRequest.responseType !== ResponseType.OK_HTTP_RESPONSE && response?.body()!= null &&
                    response.body()?.source() != null) {
                try {
                    response.body()!!.source().close()
                } catch (ignore: Exception) {

                }

            }
        }

    }

}