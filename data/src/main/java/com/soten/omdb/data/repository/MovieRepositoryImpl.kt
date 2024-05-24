package com.soten.omdb.data.repository

import com.soten.omdb.data.mapper.toDomain
import com.soten.omdb.domain.model.detail.MovieDetailModel
import com.soten.omdb.domain.model.search.MovieModel
import com.soten.omdb.domain.repository.MovieRepository
import com.soten.omdb.remote.source.MovieSearchDataSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieSearchDataSource: MovieSearchDataSource
) : MovieRepository {

    override suspend fun getSearchMovies(query: String, page: Int): List<MovieModel>{
        return movieSearchDataSource.getSearchMovies(query, page).search.map { it.toDomain() }
    }

    override suspend fun getMovieDetail(imdbID: String): MovieDetailModel {
        return movieSearchDataSource.getMovieDetail(imdbID).toDomain()
    }
}