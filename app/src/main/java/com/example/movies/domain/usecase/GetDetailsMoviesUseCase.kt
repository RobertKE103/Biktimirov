package com.example.movies.domain.usecase

import com.example.movies.domain.entity.details.MainDetails
import com.example.movies.domain.repository.MoviesRepository
import retrofit2.Response
import javax.inject.Inject

class GetDetailsMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(id: Int): Response<MainDetails>{
        return repository.getDetailsMovies(id)
    }

}