package com.junsu.sample.ui.liveData

import androidx.fragment.app.viewModels
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentLiveDataBinding
import com.junsu.sample.ui.AppBaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LiveDataFragment : AppBaseFragment<FragmentLiveDataBinding>() {

    private val viewModel by viewModels<LiveDataViewModel>()

    override fun getLayoutResId() = R.layout.fragment_live_data

    override fun initFragment() {
        activity?.title = getString(R.string.menu_liveData)

        bind.addCommonData.setOnClickListener {
            commonViewModel.addNumberData()
        }

        bind.addFragmentData.setOnClickListener {
            viewModel.addNumberData()
        }

        viewModel.numberData.observe(viewLifecycleOwner) {
            bind.fragmentData.text = it.toString()
        }

        commonViewModel.numberData.observe(viewLifecycleOwner) {
            bind.commonData.text = it.toString()
        }
    }

}
