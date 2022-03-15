package com.example.newsfeedapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeedapplication.NewsFeedFragmentDirections
import com.example.newsfeedapplication.databinding.NewsFeedItemBinding
import com.example.newsfeedapplication.model.News

class NewsFeedAdapter(var news: List<News>?, private val clickListener: NewsFeedItemOnClickListener?) :
    RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {

    fun interface NewsFeedItemOnClickListener {
        fun onClick(id: Int)
    }

    class NewsFeedViewHolder(
        val binding: NewsFeedItemBinding,
        clickListener: NewsFeedItemOnClickListener?,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        var id = -1

        init {
            binding.root.setOnClickListener { clickListener?.onClick(id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder =
        NewsFeedViewHolder(
            NewsFeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickListener
        )

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        holder.id = position
        holder.binding.news = news!![position]
    }

    override fun getItemCount(): Int = news?.size ?: 0
}