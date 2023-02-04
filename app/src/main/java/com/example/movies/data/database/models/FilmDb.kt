package com.example.movies.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class FilmDb(
    val countries: List<CountryDb>,
    val filmId: Int,
    val filmLength: String,
    val genres: List<GenreDb>,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingChange: Any,
    val ratingVoteCount: Int,
    val year: String
)