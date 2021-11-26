package com.junsu.sample.ui.list.paged.room

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.junsu.base.BaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentListPagedRoomBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RoomPagedListFragment : BaseFragment<FragmentListPagedRoomBinding>() {

    private val viewModel by viewModels<RoomPagedListViewModel>()

    override fun getLayoutResId() = R.layout.fragment_list_paged_room

    override fun initFragment() {
        activity?.title = getString(R.string.menu_list_paged_room)
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
