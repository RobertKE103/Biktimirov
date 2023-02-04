package com.example.movies.domain.entity.popularAndSearch

import com.example.movies.domain.entity.details.Country
import com.example.movies.domain.entity.details.Genre

data class Film(
    val countries: List<Country>,
    val filmId: Int,
    val genres: List<Genre>,
    val nameRu: String,
    val posterUrlPreview: String,
    val year: String,
    var isFavorite: Boolean = false
)