package com.junsu.sample.ui.main

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.junsu.base.BaseFragment
import com.junsu.sample.R
import com.junsu.sample.databinding.FragmentMainBinding
import com.junsu.sample.ui.main.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel by viewModels<MainViewModel>()

    override fun getLayoutResId() = R.layout.fragment_main

    override fun initFragment() {
    }

    override fun onResume() {
        super.onResume()
        activity?.title = getString(R.string.menu_main)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_blank -> MainFragmentDirections.actionBlankFragment()
            R.id.menu_action_list_paged_network -> MainFragmentDirections.actionNetworkPagedListFragment()
            R.id.menu_action_list_paged_room -> MainFragmentDirections.actionRoomPagedListFragment()
            R.id.menu_action_notification -> MainFragmentDirections.actionNotificationFragment()
            else -> null
        }?.also {
            findNavController().navigate(it)
        }
        return super.onOptionsItemSelected(item)
    }
}