package com.soten.omdb.data.mapper

import com.soten.omdb.domain.model.detail.MovieDetailModel
import com.soten.omdb.domain.model.detail.RatingModel
import com.soten.omdb.remote.api.response.detail.MovieDetailResponse
import com.soten.omdb.remote.api.response.detail.RatingResponse

fun MovieDetailResponse.toDomain() =
    MovieDetailModel(
        imdbID = imdbID,
        title = title,
        year = year,
        rated = rated,
        released = released,
        runtime = runtime,
        genre = genre,
        director = director,
        writer = writer,
        actors = actors,
        plot = plot,
        language = language,
        country = country,
        awards = awards,
        poster = poster,
        metascore = metascore,
        imdbRating = imdbRating,
        imdbVotes = imdbVotes,
        type = type,
        boxOffice = boxOffice,
        production = production,
        website = website,
        dvd = dvd,
        ratings = ratings.map { it.toDomain() },
        response = response
    )

fun RatingResponse.toDomain() =
    RatingModel(
        source = source,
        value = value
    )