package com.junsu.base.extension

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File

fun Fragment.startGetContent(requestCode: Int, title: String = "Select", type: String = "image/*") {
    startActivityForResult(Intent.createChooser(Intent().apply {
        this.type = type
        this.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        this.action = Intent.ACTION_GET_CONTENT
    }, title), requestCode)
}

fun Fragment.startImageCapture(requestCode: Int, file: File) {
    context?.also { c->
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(c.packageManager)

        file.absolutePath
        val photoURI: Uri = FileProvider.getUriForFile(
            c,
            "${c.packageName}.file_provider",
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(intent, requestCode)
    }
}

fun Fragment.startEmail(email: String, subject: String = "", message: String="", chooserTitle: String = "Choose Email Client.") {
    Intent(Intent.ACTION_SEND).apply {
        type = "plain/Text"
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
        type = "message/rfc822"
        startActivity(Intent.createChooser(this, chooserTitle))
    }
}