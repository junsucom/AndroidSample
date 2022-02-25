package com.junsu.sample.ui.blank

import androidx.fragment.app.viewModels
import com.junsu.sample.ui.AppBaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentBlankBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlankFragment : AppBaseFragment<FragmentBlankBinding>() {

    private val viewModel by viewModels<BlankViewModel>()

    override fun getLayoutResId() = R.layout.fragment_blank

    override fun initFragment() {
        activity?.title = getString(R.string.menu_blank)
    }

}
