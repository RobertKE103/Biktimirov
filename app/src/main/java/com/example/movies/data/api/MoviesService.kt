package com.example.movies.data.api

import com.example.movies.domain.entity.details.DetailsResponse
import com.example.movies.domain.entity.popularAndSearch.ListMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesService {

    @GET("api/v2.2/films/top")
    suspend fun getListPopular(
        @Query("type") type: String = "TOP_100_POPULAR_FILMS"
    ): Response<ListMovies>

    @GET("api/v2.2/films/{id}")
    suspend fun getDetails(
        @Path("id") id: Int
    ): Response<DetailsResponse>

    @GET("api/v2.1/films/search-by-keyword")
    suspend fun getFromSearchMovies(
        @Query("keyword") keyword: String? = null
    ): Response<ListMovies>

}