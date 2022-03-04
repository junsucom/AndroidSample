package com.junsu.base.extension

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

fun File.getMimeType(fallback: String = "*/*"): String {
    return MimeTypeMap.getFileExtensionFromUrl(toString())
        ?.run { MimeTypeMap.getSingleton().getMimeTypeFromExtension(toLowerCase(Locale.ROOT)) }
        ?: fallback
}


fun File.saveToDownload(resolver: ContentResolver, mimeType: String = this.getMimeType(), displayName: String = this.name) {
    val values = ContentValues()
    values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
    values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)

    //Log.d("test", "file:${absolutePath}")
    var uri: Uri? = null
    try {
        val contentUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // 외부저장소-Downloads에 저장하고 싶을 때(Q 이상)
            MediaStore.Downloads.EXTERNAL_CONTENT_URI
        } else {
            // 외부저장소-Pictures에 저장하고 싶을 때
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        uri = resolver.insert(contentUri, values)
        if (uri == null) {
            throw IOException("Failed to create new MediaStore record.")
        }
        resolver.openOutputStream(uri).use { stream ->
            if (stream == null) {
                throw IOException("Failed to open output stream.")
            }
            // 파일을 바이트배열로 변환 후, 파일에 저장
            val bArray: ByteArray = convertFileToByteArray(this)
            stream.write(bArray)
            stream.flush()
            stream.close()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.clear()
                // 파일 저장이 완료되었으니, IS_PENDING을 다시 0으로 설정한다.
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                // 파일을 업데이트하면, 파일이 보인다.
                resolver.update(uri, values, null, null)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        if (uri != null) {
            resolver.delete(uri, null, null)
        }

        throw Error("사진을 저장할 수 없습니다.")
    }
}

private fun convertFileToByteArray(file: File): ByteArray {
    var fis: FileInputStream? = null
    // Creating bytearray of same length as file
    val bArray = ByteArray(file.length().toInt())
    try {
        fis = FileInputStream(file)
        // Reading file content to byte array
        fis.read(bArray)
        fis.close()
    } catch (ioExp: IOException) {
        ioExp.printStackTrace()
    } finally {
        if (fis != null) {
            try {
                fis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return bArray
}