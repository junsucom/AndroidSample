package com.junsu.sample.ui.blank

import com.junsu.base.BaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentBlankBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BlankFragment : BaseFragment<FragmentBlankBinding>() {

    private val viewModel by viewModel<BlankViewModel>()

    override fun getLayoutResId() = R.layout.fragment_blank

    override fun initFragment() {
        activity?.title = getString(R.string.menu_blank)
    }

}
