package com.devyash.errorandeventhandling.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devyash.errorandeventhandling.R
import com.devyash.errorandeventhandling.databinding.LoaderLayoutBinding

class LoaderAdapter: LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    inner class LoaderViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding = LoaderLayoutBinding.bind(itemView)

        fun bind(loadState: LoadState){
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.loader_layout,parent,false)
        return LoaderViewHolder(viewHolder)
    }
}