package com.soten.omdb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soten.omdb.domain.model.search.MovieModel
import com.soten.omdb.domain.repository.MovieRepository
import java.io.IOException
import javax.inject.Inject

class MoviePagingDataSource @Inject constructor(
    private val movieRepository: MovieRepository,
    private val query: String,
) : PagingSource<Int, MovieModel>() {

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val movies = movieRepository.getSearchMovies(query, page)

            LoadResult.Page(
                data = movies,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}