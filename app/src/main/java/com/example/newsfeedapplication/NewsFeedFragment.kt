package com.example.newsfeedapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeedapplication.adapters.NewsFeedAdapter
import com.example.newsfeedapplication.databinding.FragmentNewsFeedBinding
import com.example.newsfeedapplication.viewmodel.NewsViewModel

class NewsFeedFragment : Fragment() {

    private var _binding: FragmentNewsFeedBinding? = null

    private val binding get() = _binding!!
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =  NewsFeedAdapter(null) {
            findNavController().navigate(NewsFeedFragmentDirections.actionToDetailsView(it))
        }

        binding.feed.adapter = adapter
        binding.feed.layoutManager = LinearLayoutManager(context)
        binding.refresher.isRefreshing = true

        viewModel.news.observe(viewLifecycleOwner) {
            adapter.news = it
            adapter.notifyDataSetChanged()
            binding.refresher.isRefreshing = false
        }

        binding.refresher.setOnRefreshListener {
            viewModel.updateData { binding.refresher.isRefreshing = false }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}