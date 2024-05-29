package com.soten.omdb.ui.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.soten.omdb.databinding.ActivitySearchBinding
import com.soten.omdb.extension.isEmpty
import com.soten.omdb.extension.textChangesToFlow
import com.soten.omdb.ui.detail.DetailActivity
import com.soten.omdb.ui.search.adapter.MovieAdapter
import com.soten.omdb.ui.search.adapter.MovieLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    private val searchViewModel by viewModels<SearchViewModel>()

    private val movieAdapter = MovieAdapter { imdbID ->
        startActivity(DetailActivity.newInstance(this, imdbID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindViews()

        setAdapter()
        observeData()
    }


    @OptIn(FlowPreview::class)
    private fun bindViews() {
        binding.mediaSearchBar
            .textChangesToFlow()
            .debounce(DEBOUNCE_TIME)
            .onEach {
                val input = it.toString()
                movieAdapter.submitData(PagingData.empty())
                searchViewModel.searchMovies(input)
            }.launchIn(lifecycleScope)

        binding.errorContainer.setOnClickListener {
            movieAdapter.retry()
        }
    }

    private fun setAdapter() {
        binding.movieRecyclerView.adapter =
            movieAdapter.withLoadStateFooter(footer = MovieLoadStateAdapter { movieAdapter.retry() })

        movieAdapter.addLoadStateListener { loadState ->
            val isListEmpty = movieAdapter.isEmpty()
            val isListError = loadState.refresh is LoadState.Error

            binding.errorContainer.isVisible = isListError && isListEmpty
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            searchViewModel.movies.collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    companion object {
        private const val DEBOUNCE_TIME = 600L
    }
}