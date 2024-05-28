package com.soten.omdb.domain.usecase

import com.soten.omdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(imdbID: String) = flow {
        emit(movieRepository.getMovieDetail(imdbID))
    }
}