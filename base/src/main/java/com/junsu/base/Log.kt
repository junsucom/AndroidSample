package com.junsu.base

interface Log {
    companion object {
        private const val REAL_METHOD_POS = 2

        private fun getPrefix(): String {
            val real = Throwable().stackTrace[REAL_METHOD_POS]
            return "[${real.fileName}(${real.lineNumber})/${real.methodName}()] "
        }

        private fun getPrefix2(): String {
            val real = Throwable().stackTrace[3]
            return "[${real.fileName}(${real.lineNumber})/${real.methodName}()] "
        }

        private fun getFileName(): String {
            val real = Throwable().stackTrace[REAL_METHOD_POS]
            return real.fileName.substring(0, real.fileName.lastIndexOf("."))
        }

        private fun getMethodName(): String {
            val real = Throwable().stackTrace[REAL_METHOD_POS]
            return real.methodName
        }

        fun v() {
            android.util.Log.v(
                getFileName(),
                getPrefix()
            )
        }

        fun v(msg: String?) {
            android.util.Log.v(getFileName(), getPrefix() + msg)
        }

        fun v(tag: String, msg: String?) {
            android.util.Log.v(tag, getPrefix() + msg)
        }

        fun d() {
            android.util.Log.d(
                getFileName(),
                getPrefix()
            )
        }

        fun d(msg: String?) {
            android.util.Log.d(getFileName(), getPrefix() + msg)
        }

        fun d(tag: String, msg: String?) {
            android.util.Log.d(tag, getPrefix() + msg)
        }

        fun i() {
            android.util.Log.i(
                getFileName(),
                getPrefix()
            )
        }

        fun i(tag: String, msg: String?) {
            android.util.Log.i(tag, getPrefix() + msg)
        }

        fun w() {
            android.util.Log.w(
                getFileName(),
                getPrefix()
            )
        }

        fun w(msg: String?) {
            android.util.Log.w(getFileName(), getPrefix() + msg)
        }

        fun w(tag: String, msg: String?) {
            android.util.Log.w(tag, getPrefix() + msg)
        }

        fun e() {
            android.util.Log.e(
                getFileName(),
                getPrefix()
            )
        }

        fun e(msg: String?) {
            android.util.Log.e(getFileName(), getPrefix() + msg)
        }

        fun e(tag: String, msg: String?) {
            android.util.Log.e(tag, getPrefix() + msg)
        }

        fun wtf() {
            android.util.Log.wtf(
                getFileName(),
                getPrefix()
            )
        }

        fun wtf(msg: String?) {
            android.util.Log.wtf(getFileName(), getPrefix() + msg)
        }

        fun wtf(tr: Throwable) {
            android.util.Log.wtf(
                getFileName(),
                getPrefix(), tr)
        }

        fun wtf(msg: String?, tr: Throwable) {
            android.util.Log.wtf(getFileName(), getPrefix() + msg, tr)
        }

        fun wtf(tag: String, msg: String?, tr: Throwable) {
            android.util.Log.wtf(tag, getPrefix() + msg, tr)
        }

        fun i2() {
            android.util.Log.i(
                getFileName(),
                getPrefix2()
            )
        }

        fun e2() {
            android.util.Log.e(
                getFileName(),
                getPrefix2()
            )
        }
    }
}