package com.example.newsfeedapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.newsfeedapplication.databinding.FragmentDetailsViewBinding
import com.example.newsfeedapplication.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsViewFragment : Fragment() {

    private var _binding: FragmentDetailsViewBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var detailsViewModelFactory: DetailsViewModel.DetailsViewModelFactory
    private val viewModel: DetailsViewModel by activityViewModels {
        DetailsViewModel.provideFactory(
            detailsViewModelFactory,
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

        viewModel.newsId = args.newsId

        binding.lifecycleOwner = viewLifecycleOwner
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