package com.pokemon.ui.adapter.footer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pokemon.shared.extension.gone
import com.pokemon.shared.extension.visible
import com.pokemon.ui.databinding.LayoutPagerFooterBinding

class PagerFooterAdapter(private val onRetryClick: () -> Unit = {}) :
    LoadStateAdapter<PagerFooterAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: LayoutPagerFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Error

                when(loadState){
                    is LoadState.Loading -> {
                        progressBar.visible()
                        btnRetry.gone()
                    }
                    is LoadState.Error -> {
                        progressBar.visible()
                        btnRetry.gone()
                    }
                    is LoadState.NotLoading-> {
                        progressBar.gone()
                        btnRetry.gone()
                    }
                    else -> Unit
                }
                btnRetry.setOnClickListener {
                    onRetryClick.invoke()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding =
            LayoutPagerFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

}