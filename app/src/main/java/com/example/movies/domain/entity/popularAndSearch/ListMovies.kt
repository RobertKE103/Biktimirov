package com.example.movies.domain.entity.popularAndSearch

data class ListMovies(
    val films: List<Film>,
    val pagesCount: Int
)