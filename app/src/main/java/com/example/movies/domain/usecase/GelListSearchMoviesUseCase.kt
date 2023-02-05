package com.example.movies.domain.usecase

import androidx.paging.PagingSource
import com.example.movies.domain.entity.popularAndSearch.Film
import com.example.movies.domain.repository.MoviesRepository
import retrofit2.http.Query
import javax.inject.Inject

class GelListSearchMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(query: String): PagingSource<Int, Film>{
        return repository.getListSearchMovies(query)
    }

}