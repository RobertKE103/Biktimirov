package com.example.movies.di

import com.example.movies.data.repository.MoviesRepositoryImpl
import com.example.movies.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module

@Module
interface BindModule {
    @Binds
    fun bindMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

}