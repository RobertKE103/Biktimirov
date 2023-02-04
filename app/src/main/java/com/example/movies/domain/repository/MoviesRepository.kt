package com.example.movies.domain.repository

import androidx.paging.PagingSource
import com.example.movies.domain.entity.details.MainDetails
import com.example.movies.domain.entity.popular.Film
import retrofit2.Response
import java.util.concurrent.Flow

interface MoviesRepository {

    fun getListPopularMovies(): PagingSource<Int, Film>

    suspend fun getDetailsMovies(id: Int): Response<MainDetails>

    suspend fun addFavoriteMovies()




}