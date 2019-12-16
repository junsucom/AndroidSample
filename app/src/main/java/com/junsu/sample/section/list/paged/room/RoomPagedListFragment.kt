package com.junsu.sample.section.list.paged.room

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.junsu.base.BaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentListPagedRoomBinding
import kotlinx.android.synthetic.main.fragment_list_paged_room.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class RoomPagedListFragment : BaseFragment<FragmentListPagedRoomBinding>() {

    private val viewModel by viewModel<RoomPagedListViewModel>()

    override fun getLayoutResId() = R.layout.fragment_list_paged_room

    override fun initFragment() {
        bind.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(view_simpleList_list.adapter == null) {
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
