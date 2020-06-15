package com.junsu.base.extension

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import androidx.core.content.FileProvider
import java.io.File

fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun Activity.startGetContent(requestCode: Int, title: String = "Select", type: String = "image/*") {
    startActivityForResult(Intent.createChooser(Intent().apply {
        this.type = type
        this.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        this.action = Intent.ACTION_GET_CONTENT
    }, title), requestCode)
}

fun Activity.startImageCapture(requestCode: Int, file: File) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    intent.resolveActivity(packageManager)

    file.absolutePath
    val photoURI: Uri = FileProvider.getUriForFile(
        this,
        "${packageName}.file_provider",
        file
    )
    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
    startActivityForResult(intent, requestCode)
}

fun Activity.startEmail(email: String, subject: String = "", message: String="", chooserTitle: String = "Choose Email Client.") {
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