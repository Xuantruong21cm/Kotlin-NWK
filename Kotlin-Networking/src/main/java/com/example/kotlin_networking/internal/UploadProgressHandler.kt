package com.example.kotlin_networking.internal

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.kotlin_networking.common.KotConstants
import com.example.kotlin_networking.common.Progress

class UploadProgressHandler(val progressCallback: ((bytesUploaded: Long, totalBytes: Long) -> Unit)) : Handler(Looper.getMainLooper()) {

    override fun handleMessage(msg: Message) {
        when (msg?.what) {
            KotConstants.UPDATE -> {
                val progress: Progress = msg.obj as Progress
                progressCallback(progress.currentBytes, progress.totalBytes)
            }
            else -> super.handleMessage(msg)
        }
    }

}