package com.junsu.sample.sector.list.paged.network

import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import com.junsu.base.BaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentListPagedNetworkBinding
import com.junsu.sample.sector.list.paged.room.ItemAdapter
import kotlinx.android.synthetic.main.fragment_list_paged_room.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class NetworkPagedListFragment : BaseFragment<FragmentListPagedNetworkBinding>() {

    private val viewModel by viewModel<NetworkPagedListViewModel>()

    override fun getLayoutResId() = R.layout.fragment_list_paged_network

    override fun initFragment() {
        bind.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(view_simpleList_list.adapter == null) {
            ItemAdapter()?.also { adapter ->
                view_simpleList_list.adapter = adapter
                viewModel.itemList.observe(viewLifecycleOwner) {
                    Timber.d("itemList:${it.size}")
                    adapter.submitList(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}
