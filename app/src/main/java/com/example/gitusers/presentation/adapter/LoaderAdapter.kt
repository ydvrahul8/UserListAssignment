package com.example.gitusers.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gitusers.databinding.ItemLoaderBinding

class LoaderAdapter(private val adapter: UserPagingAdapter) : LoadStateAdapter<LoaderAdapter.LoaderViewModel>() {
    inner class LoaderViewModel(
        private val binding: ItemLoaderBinding, private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bindTo(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMsg.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }

    override fun onBindViewHolder(holder: LoaderViewModel, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewModel {
        val view = ItemLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoaderViewModel(view) { adapter.retry() }
    }
}