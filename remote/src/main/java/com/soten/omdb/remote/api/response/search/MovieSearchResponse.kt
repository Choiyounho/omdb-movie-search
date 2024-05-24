package com.soten.omdb.remote.api.response.search


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchResponse(
    @SerialName("Response")
    val response: String,
    @SerialName("Search")
    val search: List<MovieResponse>,
    @SerialName("totalResults")
    val totalResults: String
)