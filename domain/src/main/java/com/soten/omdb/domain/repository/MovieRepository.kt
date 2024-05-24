package com.soten.omdb.domain.repository

import com.soten.omdb.domain.model.detail.MovieDetailModel
import com.soten.omdb.domain.model.search.MovieModel

interface MovieRepository {

    suspend fun getSearchMovies(query: String, page: Int): List<MovieModel>

    suspend fun getMovieDetail(imdbID: String): MovieDetailModel
}