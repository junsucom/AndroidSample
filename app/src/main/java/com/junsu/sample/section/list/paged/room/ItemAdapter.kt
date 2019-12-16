package com.junsu.sample.section.list.paged.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.junsu.base.BindingViewHolder
import com.junsu.sample.R
import com.junsu.sample.databinding.CellItemBinding
import com.junsu.sample.model.Item
import timber.log.Timber

internal class ItemAdapter : PagedListAdapter<Item, ItemAdapter.ItemViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.cell_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bindTo(user)
        } else {
            holder.clear()
        }
    }

    override fun onViewRecycled(holder: ItemViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Item> = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(
                oldUser: Item, newUser: Item
            ): Boolean {
                return oldUser.id == newUser.id
            }

            override fun areContentsTheSame(
                oldUser: Item, newUser: Item
            ): Boolean {
                return oldUser.id == newUser.id
            }
        }
    }

    inner class ItemViewHolder(itemView: View) :
        BindingViewHolder<CellItemBinding, Item>(itemView) {
        override fun bindTo(data: Item) {
            binding.item = data
            binding.setClickListener {
                Timber.d("id:${binding.item?.id}")
            }
            Glide.with(binding.viewCellItemImage).load(data.image).into(binding.viewCellItemImage)
        }

        override fun clear() {

        }
    }
}