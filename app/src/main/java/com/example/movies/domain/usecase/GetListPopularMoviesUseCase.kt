package com.example.movies.domain.usecase

import androidx.paging.PagingSource
import com.example.movies.domain.entity.popular.Film
import com.example.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetListPopularMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): PagingSource<Int, Film> {
        return repository.getListPopularMovies()
    }
}