package com.soten.omdb.data.mapper

import com.soten.omdb.domain.model.search.MovieModel
import com.soten.omdb.remote.api.response.search.MovieResponse

fun MovieResponse.toDomain(): MovieModel =
    MovieModel(
        imdbID = imdbID,
        poster = poster,
        title = title,
        type = type,
        year = year
    )