package com.soten.omdb.domain.model.search

data class MovieModel(
    val imdbID: String,
    val poster: String,
    val title: String,
    val type: String,
    val year: String
)
