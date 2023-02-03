package com.example.movies.domain.entity.popular

data class ListMovies(
    val films: List<Film>,
    val pagesCount: Int
)