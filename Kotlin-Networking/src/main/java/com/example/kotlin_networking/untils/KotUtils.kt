package com.example.kotlin_networking.untils

import com.example.kotlin_networking.common.KotConstants
import com.example.kotlin_networking.core.Core
import com.example.kotlin_networking.erro.KotError
import com.example.kotlin_networking.request.KotRequest
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URLConnection

class KotUtils private constructor() {

    companion object {

        fun getErrorForConnection(kotError: KotError): KotError {
            kotError.errorDetail = KotConstants.CONNECTION_ERROR
            kotError.errorCode = 0
            return kotError
        }

        fun getErrorForServerResponse(kotError: KotError, kotRequest: KotRequest, code: Int): KotError {
            val parsedKotError = kotRequest.parseNetworkError(kotError)
            parsedKotError.errorDetail = KotConstants.RESPONSE_FROM_SERVER_ERROR
            parsedKotError.errorCode = code
            return parsedKotError
        }

        fun getErrorForParse(kotError: KotError): KotError {
            kotError.errorCode = 0
            kotError.errorDetail = KotConstants.PARSE_ERROR
            return kotError
        }

        fun getMimeType(path: String): String {
            val fileNameMap = URLConnection.getFileNameMap()
            var mimeType: String? = fileNameMap.getContentTypeFor(path)
            if (mimeType == null) {
                mimeType = "application/octet-stream"
            }
            return mimeType
        }

        fun saveFile(response: Response?, dirPath: String?, fileName: String?) {
            val inputStream: InputStream? = response?.body()?.byteStream()

            val dir = File(dirPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }

            val file = File(dir, fileName)
            val fos = FileOutputStream(file)
            inputStream.use { input ->
                fos.use { output ->
                    if (output is FileOutputStream) input?.copyTo(output)
                }
            }
        }

        fun sendAnalytics(analyticsListener: ((timeTakenInMillis: Long, bytesSent: Long, bytesReceived: Long, isFromCache: Boolean) -> Unit)?,
                          timeTaken: Long, bytesSent: Long, bytesReceived: Long, isFromCache: Boolean) {
            Core.instance.executorSupplier.forMainThreadTasks().execute {
                analyticsListener?.invoke(timeTaken, bytesSent, bytesReceived, isFromCache)
            }
        }

    }

}