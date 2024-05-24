package com.soten.omdb.remote.source

import com.soten.omdb.remote.api.MovieSearchApi
import javax.inject.Inject

class MovieSearchDataSource @Inject constructor(
    private val movieSearchApi: MovieSearchApi
) {

    suspend fun getSearchMovies(query: String, page: Int) = movieSearchApi.getSearchMovies(query, page)

    suspend fun getMovieDetail(query: String) = movieSearchApi.getMovieDetail(query)
}