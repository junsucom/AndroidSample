package com.junsu.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.lang.Exception

object NetworkUtils {
    /**
     * Android 10에서 지원 중단되었습니다. Android 10(API 수준 29) 이상을 타겟팅하는 앱에는 NetworkCallbacks를 대신 사용하세요.
     */
    fun isConnected(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            activeNetwork?.isConnectedOrConnecting == true
        } catch (e: Exception) {
            true
        }
    }
}