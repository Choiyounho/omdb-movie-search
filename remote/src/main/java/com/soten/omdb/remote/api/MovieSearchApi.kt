package com.soten.omdb.remote.api

import com.soten.omdb.remote.api.ApiInfo.EndPoints.OMDB
import com.soten.omdb.remote.api.response.detail.MovieDetailResponse
import com.soten.omdb.remote.api.response.search.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchApi {

    @GET(OMDB)
    suspend fun getSearchMovies(
        @Query("s") query: String,
        @Query("page") page: Int = 1,
        @Query("type") type: String = "movie",
        @Query("apikey") apikey: String = ApiInfo.API_KEY,
    ): MovieSearchResponse

    @GET(OMDB)
    suspend fun getMovieDetail(
        @Query("i") imdbID: String,
        @Query("apikey") apikey: String = ApiInfo.API_KEY,
    ): MovieDetailResponse
}