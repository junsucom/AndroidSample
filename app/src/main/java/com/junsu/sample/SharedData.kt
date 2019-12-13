package com.junsu.sample

import com.junsu.base.SharedPreferenceUtil

object SharedData : SharedPreferenceUtil(Define.TAG) {
    var inited by booleanPref("inited", false)

    fun clear() {
        inited = false
    }
}