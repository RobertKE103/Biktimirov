package com.example.movies.domain.usecase

import com.example.movies.domain.entity.popularAndSearch.Film
import com.example.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(): List<Film> {
        return repository.getFavoriteMovies()
    }


}