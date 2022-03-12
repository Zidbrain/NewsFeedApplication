package com.example.newsfeedapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeedapplication.NewsFeedFragmentDirections
import com.example.newsfeedapplication.databinding.NewsFeedItemBinding
import com.example.newsfeedapplication.model.News

class NewsFeedAdapter(var news: List<News>?) : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {
    class NewsFeedViewHolder(val binding: NewsFeedItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            binding.root.findNavController().navigate(NewsFeedFragmentDirections.actionToDetailsView(adapterPosition))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder =
        NewsFeedViewHolder(NewsFeedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        holder.binding.news = news!![position]
    }

    override fun getItemCount(): Int = news?.size ?: 0
}