package com.junsu.sample.ui

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import com.junsu.base.BaseFragment

abstract class AppBaseFragment<T : ViewDataBinding> : BaseFragment<T>() {

    val commonViewModel by activityViewModels<AppViewModel>()

}

