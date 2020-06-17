package com.junsu.sample.ui.list.paged.room

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.junsu.base.BaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentListPagedRoomBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_paged_room.*
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
        if (view_simpleList_list.adapter == null) {
            ItemAdapter()?.also { adapter ->
                view_simpleList_list.adapter = adapter
                viewModel.itemList.observe(viewLifecycleOwner) {
                    Timber.d("noticeList:${it.size}")
                    adapter.submitList(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}
