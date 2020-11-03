package com.example.kotlin_networking.internal

import com.example.kotlin_networking.common.KotConstants
import com.example.kotlin_networking.common.Progress
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

class RequestProgressBody(private val requestBody: RequestBody, progressCallback: ((bytesUploaded: Long, totalBytes: Long) -> Unit)?) : RequestBody() {

    private var bufferedSink: BufferedSink? = null
    private var progressHandler: UploadProgressHandler? = null

    init {
        if (progressCallback != null) {
            this.progressHandler = UploadProgressHandler(progressCallback)
        }
    }

    override fun contentType(): MediaType? = requestBody.contentType()

    @Throws(IOException::class)
    override fun contentLength(): Long = requestBody.contentLength()

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(sink(sink))
        }
        requestBody.writeTo(bufferedSink!!)
        bufferedSink?.flush()
    }

    private fun sink(sink: Sink): Sink {
        return object : ForwardingSink(sink) {
            internal var bytesWritten = 0L
            internal var contentLength = 0L

            @Throws(IOException::class)
            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)
                if (contentLength == 0L) {
                    contentLength = contentLength()
                }
                bytesWritten += byteCount
                progressHandler?.obtainMessage(KotConstants.UPDATE,
                        Progress(bytesWritten, contentLength))?.sendToTarget()
            }
        }
    }
}