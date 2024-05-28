package com.soten.omdb.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.soten.omdb.data.paging.MoviePagingDataSource
import com.soten.omdb.domain.model.search.MovieModel
import com.soten.omdb.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<MovieModel>>(PagingData.empty())
    val movies = _movies.asStateFlow()

    init {
        searchMovies()
    }

    fun searchMovies(query: String = DEFAULT_QUERY) {
        viewModelScope.launch {
            Pager(PagingConfig(pageSize = 10)) {
                MoviePagingDataSource(movieRepository, query)
            }.flow
                .cachedIn(this)
                .collect { pagingData ->
                    _movies.value = pagingData
                }
        }
    }

    companion object {
        private const val DEFAULT_QUERY = "star"
    }
}