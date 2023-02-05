package com.example.movies.domain.usecase

import androidx.lifecycle.LiveData
import com.example.movies.domain.entity.popularAndSearch.Film
import com.example.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(): LiveData<List<Film>> {
        return repository.getFavoriteMovies()
    }


}