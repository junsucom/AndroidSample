package com.junsu.sample.ui.list.paged.network

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.junsu.sample.ui.AppBaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentListPagedNetworkBinding
import com.junsu.sample.ui.list.paged.room.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NetworkPagedListFragment : AppBaseFragment<FragmentListPagedNetworkBinding>() {

    private val viewModel by viewModels<NetworkPagedListViewModel>()

    override fun getLayoutResId() = R.layout.fragment_list_paged_network

    override fun initFragment() {
        activity?.title = getString(R.string.menu_list_paged_network)
        bind.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (bind.viewSimpleListList.adapter == null) {
            ItemAdapter()?.also { adapter ->
                bind.viewSimpleListList.adapter = adapter
                lifecycleScope.launch {
                    viewModel.flow.collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
        }
    }
}
