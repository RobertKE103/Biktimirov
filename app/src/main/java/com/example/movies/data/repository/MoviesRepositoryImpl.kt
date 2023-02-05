package com.example.movies.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import androidx.paging.PagingSource
import com.example.movies.data.api.MoviesApiPageSource
import com.example.movies.data.api.MoviesApiPageSource.Companion.POPULAR_TYPE
import com.example.movies.data.api.MoviesApiPageSource.Companion.SEARCH_TYPE
import com.example.movies.data.api.MoviesService
import com.example.movies.data.database.FavoriteDao
import com.example.movies.data.mappers.toFilm
import com.example.movies.data.mappers.toFilmDb
import com.example.movies.domain.entity.details.DetailsResponse
import com.example.movies.domain.entity.popularAndSearch.Film
import com.example.movies.domain.repository.MoviesRepository
import retrofit2.Response
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val moviesApiPageSource: MoviesApiPageSource.Factory,
    private val favoriteDao: FavoriteDao
): MoviesRepository {

    override fun getListPopularMovies(): PagingSource<Int, Film> {
        return moviesApiPageSource.create(POPULAR_TYPE)
    }

    override fun getListSearchMovies(query: String): PagingSource<Int, Film> {
        return moviesApiPageSource.create(SEARCH_TYPE, query)
    }

    override suspend fun getDetailsMovies(id: Int): Response<DetailsResponse> {
        return moviesService.getDetails(id)
    }

    override suspend fun addFavoriteMovies(film: Film) {
        favoriteDao.insertFilm(film.toFilmDb())
    }

    override fun getFavoriteMovies(): LiveData<List<Film>> =
        MediatorLiveData<List<Film>>().apply {
            addSource(favoriteDao.getAllNews()) { list ->
                value = list.map { it.toFilm() }
            }

        }

    override suspend fun deleteFavoriteFilm(id: Int) {
        favoriteDao.deleteFilm(id)
    }


}