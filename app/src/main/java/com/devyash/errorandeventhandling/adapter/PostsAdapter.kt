package com.devyash.errorandeventhandling.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devyash.errorandeventhandling.R
import com.devyash.errorandeventhandling.databinding.ItemLayoutBinding
import com.devyash.errorandeventhandling.models.posts
import com.devyash.errorandeventhandling.models.postsItem

class PostsAdapter(val postItemList:List<postsItem>):RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        var binding = ItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val viewHolder = PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false))

        return viewHolder
    }

    override fun getItemCount(): Int {
        return postItemList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postItemList[position]

        holder.binding.tvId.text = post.id.toString()
        holder.binding.tvTitle.text = post.title.toString()
        holder.binding.tvBody.text = post.body.toString()
    }
}