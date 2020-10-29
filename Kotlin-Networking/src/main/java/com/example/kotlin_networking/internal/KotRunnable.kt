package com.example.kotlin_networking.internal

import com.example.kotlin_networking.common.KotResponse
import com.example.kotlin_networking.common.Priority
import com.example.kotlin_networking.common.RequestType
import com.example.kotlin_networking.common.ResponseType
import com.example.kotlin_networking.core.Core
import com.example.kotlin_networking.erro.KotError
import com.example.kotlin_networking.request.KotRequest
import com.example.kotlin_networking.untils.KotUtils
import com.example.kotlin_networking.untils.SourceCloseUtil
import okhttp3.Response

class KotRunnable(val request: KotRequest) : Runnable {

    val priority: Priority = request.priority
    val sequence: Int = request.sequenceNumber

    override fun run() {
        when (request.requestType) {
            RequestType.SIMPLE -> executeSimpleRequest()
            RequestType.DOWNLOAD -> executeDownloadRequest()
            RequestType.MULTIPART -> executeUploadRequest()
        }
    }

    private fun executeSimpleRequest() {
        var okHttpResponse: Response? = null
        try {

            okHttpResponse = InternalNetworking.makeSimpleRequestAndGetResponse(request)

            if (okHttpResponse == null) {
                deliverError(request, KotUtils.getErrorForConnection(KotError()))
                return
            }

            if (request.responseType == ResponseType.OK_HTTP_RESPONSE) {
                request.deliverOkHttpResponse(okHttpResponse)
            }

            if (okHttpResponse.code() >= 400) {
                deliverError(request, KotUtils.getErrorForServerResponse(KotError(okHttpResponse),
                        request, okHttpResponse.code()))
                return
            }

            val kotResponse: KotResponse<*>? = request.parseResponse(okHttpResponse)

            kotResponse?.let {
                if (!kotResponse.isSuccess()) {
                    kotResponse.error?.let { error -> deliverError(request, error) }
                    return
                }

                kotResponse.response = okHttpResponse as Response
                request.deliverResponse(kotResponse)
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            deliverError(request, KotUtils.getErrorForConnection(KotError(ex)))
        } finally {
            SourceCloseUtil.close(okHttpResponse, request)
        }
    }


    private fun executeUploadRequest() {
        var okHttpResponse: Response? = null

        try {
            okHttpResponse = InternalNetworking.makeUploadRequestAndGetResponse(request)

            if (okHttpResponse == null) {
                deliverError(request, KotUtils.getErrorForConnection(KotError()))
                return
            }

            if (request.responseType == ResponseType.OK_HTTP_RESPONSE) {
                request.deliverOkHttpResponse(okHttpResponse)
            }

            if (okHttpResponse.code() >= 400) {
                deliverError(request, KotUtils.getErrorForServerResponse(KotError(okHttpResponse),
                        request, okHttpResponse.code()))
                return
            }

            val kotResponse: KotResponse<*>? = request.parseResponse(okHttpResponse)

            kotResponse?.let {
                if (!kotResponse.isSuccess()) {
                    kotResponse.error?.let { error -> deliverError(request, error) }
                    return
                }

                kotResponse.response = okHttpResponse as Response
                request.deliverResponse(kotResponse)
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            deliverError(request, KotUtils.getErrorForConnection(KotError(ex)))
        } finally {
            SourceCloseUtil.close(okHttpResponse, request)
        }
    }

    private fun executeDownloadRequest() {
        var okHttpResponse: Response?
        try {
            okHttpResponse = InternalNetworking.makeDownloadRequestAndGetResponse(request)
            if (okHttpResponse == null) {
                deliverError(request, KotUtils.getErrorForConnection(KotError()))
                return
            }

            if (okHttpResponse.code() >= 400) {
                deliverError(request, KotUtils.getErrorForServerResponse(KotError(okHttpResponse), request, okHttpResponse.code()))
                return
            }
            request.updateDownloadCompletion()
        } catch (ex: Exception) {
            ex.printStackTrace()
            deliverError(request, KotUtils.getErrorForConnection(KotError(ex)))
        }
    }

    private fun deliverError(kotRequest: KotRequest, kotError: KotError) {
        Core.instance.executorSupplier.forMainThreadTasks().execute {
            kotRequest.deliverError(kotError)
            kotRequest.finish()
        }
    }

}