package com.example.news2.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news2.R
import com.example.news2.data.models.Post
import com.example.news2.data.uiState.UIState
import com.example.news2.data.viewModels.NewsViewModel
import com.example.news2.data.viewModels.SourcesViewModel
import com.example.news2.databinding.FragmentPostsBinding
import com.example.news2.ui.adapters.PostsAdapter

class PostsFragment : Fragment() {

    private val sourcesViewModel: SourcesViewModel by activityViewModels()
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var navController: NavController
    private val binding: FragmentPostsBinding by lazy {
        FragmentPostsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = (parentFragment as? SourcesFragment)!!.findNavController()

        sourcesViewModel.combinedLiveData.observe(viewLifecycleOwner) { pair ->

            newsViewModel.clearNews()
            newsViewModel.getNews(pair.first, pair.second, requireContext())

        }
        newsViewModel.newsResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {

                    binding.progressBar.visibility = View.VISIBLE
                    binding.postsList.visibility = View.GONE
                    binding.error.visibility = View.GONE
                }

                is UIState.Success -> {

                    binding.progressBar.visibility = View.GONE
                    if (newsViewModel.newsList.value!!.isNotEmpty()) {

                        binding.postsList.visibility = View.VISIBLE
                        binding.error.visibility = View.GONE
                        newsViewModel.setNews(state.data.articles)
                        val adapter = PostsAdapter(newsViewModel.newsList.value!!)
                        binding.postsList.layoutManager = LinearLayoutManager(requireContext())
                        binding.postsList.adapter = adapter
                        adapter.setOnClickListener(object :
                            PostsAdapter.OnClickListener {
                            override fun onClick(position: Int, model: Post) {
                                val url = bundleOf("url" to state.data.articles[position].url)
                                navController.navigate(
                                    R.id.action_sourcesFragment_to_webViewFragment,
                                    url
                                )
                            }
                        })

                    } else {
                        binding.error.visibility = View.VISIBLE
                        binding.postsList.visibility = View.GONE
                        binding.errorMsg.setText(getString(R.string.no_results))
                    }
                }

                is UIState.Error -> {

                    binding.progressBar.visibility = View.GONE
                    binding.postsList.visibility = View.GONE
                    binding.error.visibility = View.VISIBLE
                    binding.errorMsg.setText(state.message)
                }

                null -> {

                }
            }

        }
    }

}