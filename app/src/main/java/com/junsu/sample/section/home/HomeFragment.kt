package com.junsu.sample.section.home

import com.junsu.base.BaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModel<HomeViewModel>()

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initFragment() {
    }
}