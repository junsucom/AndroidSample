package com.junsu.sample.ui.list.paged.room

import android.os.Bundle
import android.view.View
import com.junsu.base.BaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentSimpleListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class RoomPagedListFragment : BaseFragment<FragmentSimpleListBinding>() {

    private val viewModel by viewModel<RoomPagedListViewModel>()

    override fun getLayoutResId() = R.layout.fragment_simple_list

    override fun initFragment() {
        bind.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}
