package com.example.movies.data.repository

import androidx.paging.PagingSource
import com.example.movies.data.api.MoviesApiPageSource
import com.example.movies.data.api.MoviesService
import com.example.movies.domain.entity.details.MainDetails
import com.example.movies.domain.entity.popular.Film
import com.example.movies.domain.repository.MoviesRepository
import retrofit2.Response
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val moviesApiPageSource: MoviesApiPageSource.Factory
): MoviesRepository {

    override fun getListPopularMovies(): PagingSource<Int, Film> {
        return moviesApiPageSource.create()
    }

    override suspend fun getDetailsMovies(id: Int): Response<MainDetails> {
        return moviesService.getDetails(id)
    }

    override suspend fun addFavoriteMovies() {
        TODO("Not yet implemented")
    }


}