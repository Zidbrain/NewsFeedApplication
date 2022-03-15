package com.example.newsfeedapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsfeedapplication.databinding.FragmentDetailsViewBinding
import com.example.newsfeedapplication.model.News
import com.example.newsfeedapplication.viewmodel.DetailsViewModel
import com.example.newsfeedapplication.viewmodel.DetailsViewModelFactory
import com.example.newsfeedapplication.viewmodel.NewsViewModel

class DetailsViewFragment : Fragment() {

    private var _binding: FragmentDetailsViewBinding? = null

    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by activityViewModels {
        DetailsViewModelFactory(
            requireActivity().application,
            args.newsId
        )
    }

    private val args: DetailsViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        binding.imagePreview.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.news.value!!.link))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}