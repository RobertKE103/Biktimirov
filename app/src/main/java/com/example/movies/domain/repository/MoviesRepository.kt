package com.example.movies.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.example.movies.domain.entity.details.DetailsResponse
import com.example.movies.domain.entity.popularAndSearch.Film
import retrofit2.Response

interface MoviesRepository {

    fun getListPopularMovies(): PagingSource<Int, Film>
    fun getListSearchMovies(query: String): PagingSource<Int, Film>
    suspend fun getDetailsMovies(id: Int): Response<DetailsResponse>
    suspend fun addFavoriteMovies(film: Film)
    fun getFavoriteMovies(): LiveData<List<Film>>
    suspend fun deleteFavoriteFilm(id: Int)



}