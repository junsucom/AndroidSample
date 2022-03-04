package com.junsu.sample

import com.junsu.base.SharedPreferenceUtil

class SharedData : SharedPreferenceUtil(Define.TAG) {
    var inited by booleanPref("inited", false)

    fun clear() {
        inited = false
    }
}