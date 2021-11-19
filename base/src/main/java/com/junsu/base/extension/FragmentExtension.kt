package com.junsu.base.extension

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.IdRes
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavBackStackEntry
import androidx.navigation.fragment.findNavController
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

fun <T>Fragment.setNavigationResult(key: String, value: T) {
    this.findNavController().previousBackStackEntry?.savedStateHandle?.set(
        key,
        value
    )
}

fun <T>Fragment.getNavigationResult(navBackStackEntry: NavBackStackEntry, key: String, onResult: (result: T) -> Unit) {
    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME
            && navBackStackEntry.savedStateHandle.contains(key)
        ) {
            val result = navBackStackEntry.savedStateHandle.get<T>(key)
            result?.let(onResult)
            navBackStackEntry.savedStateHandle.remove<T>(key)
        }
    }
    navBackStackEntry.lifecycle.addObserver(observer)

    viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            navBackStackEntry.lifecycle.removeObserver(observer)
        }
    })
}
