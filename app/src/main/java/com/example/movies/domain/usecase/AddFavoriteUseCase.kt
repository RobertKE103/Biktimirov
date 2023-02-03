package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

}