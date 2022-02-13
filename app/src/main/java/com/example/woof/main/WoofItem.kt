package com.example.woof.main

import android.util.Log
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.URLConnection
import kotlin.math.pow

sealed class WoofItem {
    class Loaded(val fileSize: Int, val url: String): WoofItem() {
        fun getMB() = BigDecimal(fileSize / 1024.0.pow(2)).setScale(2, RoundingMode.HALF_EVEN).toString()
        fun getFileExtension() = url.substringAfterLast('.', "").toLowerCase()
        fun getType(): Type {
            val mimeType: String = URLConnection.guessContentTypeFromName(url)
            Log.d("WoofImage", mimeType)
            return when {
                mimeType.startsWith("image") && !mimeType.endsWith("gif") -> Type.IMAGE
                mimeType.startsWith("video") -> Type.VIDEO
                mimeType.startsWith("image") && mimeType.endsWith("gif") -> Type.GIF
                else -> Type.UNKNOWN
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Loaded

            if (fileSize != other.fileSize) return false
            if (url != other.url) return false

            return true
        }

        override fun hashCode(): Int {
            var result = fileSize
            result = 31 * result + url.hashCode()
            return result
        }

        enum class Type {
            UNKNOWN,
            IMAGE,
            GIF,
            VIDEO
        }
    }

    class Error(val errorMsg: String): WoofItem()
}