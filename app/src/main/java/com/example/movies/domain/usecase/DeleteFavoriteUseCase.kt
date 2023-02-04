package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val repository: MoviesRepository
) {


    suspend operator fun invoke(id: Int) {
        return repository.deleteFavoriteFilm(id)
    }

}