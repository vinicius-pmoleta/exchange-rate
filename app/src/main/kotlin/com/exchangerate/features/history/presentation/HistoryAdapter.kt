package com.exchangerate.features.history.presentation

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.exchangerate.R
import com.exchangerate.core.data.repository.local.database.entity.HistoryEntity
import com.exchangerate.databinding.HistoryItemBinding

class HistoryAdapter : PagedListAdapter<HistoryEntity, HistoryViewHolder>(HistoryDiff()) {

    private val renderer = HistoryItemRenderer()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding: HistoryItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.history_item, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.history = renderer.render(getItem(position))
    }
}

class HistoryViewHolder(val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root)

class HistoryDiff : DiffUtil.ItemCallback<HistoryEntity>() {

    override fun areItemsTheSame(oldItem: HistoryEntity?, newItem: HistoryEntity?): Boolean {
        return oldItem?.timestamp == newItem?.timestamp
    }

    override fun areContentsTheSame(oldItem: HistoryEntity?, newItem: HistoryEntity?): Boolean {
        return oldItem == newItem
    }
}