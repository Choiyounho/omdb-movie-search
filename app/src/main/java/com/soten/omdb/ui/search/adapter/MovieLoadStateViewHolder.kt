package com.soten.omdb.ui.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.soten.omdb.databinding.ItemLoadStateFooterBinding

class MovieLoadStateViewHolder(
    private val binding: ItemLoadStateFooterBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState, retry: () -> Unit) {
        binding.loadingBar.isVisible = loadState is LoadState.Loading
        binding.retry.isVisible = loadState is LoadState.Error

        binding.retry.setOnClickListener {
            retry()
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieLoadStateViewHolder {
            return MovieLoadStateViewHolder(
                ItemLoadStateFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
            )
        }
    }

}