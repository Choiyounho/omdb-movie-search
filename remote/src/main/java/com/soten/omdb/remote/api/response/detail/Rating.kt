package com.soten.omdb.remote.api.response.detail


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    @SerialName("Source")
    val source: String,
    @SerialName("Value")
    val value: String
)