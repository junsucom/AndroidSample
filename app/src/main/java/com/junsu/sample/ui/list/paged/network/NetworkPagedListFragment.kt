package com.junsu.sample.ui.list.paged.network

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.junsu.base.BaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentListPagedNetworkBinding
import com.junsu.sample.ui.blank.BlankViewModel
import com.junsu.sample.ui.list.paged.room.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_paged_room.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class NetworkPagedListFragment : BaseFragment<FragmentListPagedNetworkBinding>() {

    private val viewModel by viewModels<NetworkPagedListViewModel>()

    override fun getLayoutResId() = R.layout.fragment_list_paged_network

    override fun initFragment() {
        activity?.title = getString(R.string.menu_list_paged_network)
        bind.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (view_simpleList_list.adapter == null) {
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
