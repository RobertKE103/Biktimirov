package com.example.movies.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FilmDb(
    @Embedded val countries: List<CountryDb>,
    @PrimaryKey @ColumnInfo(name = "id") var filmId: Int,
    @Embedded val genres: List<GenreDb>,
    @ColumnInfo(name = "normalNameMovie") val nameRu: String,
    @ColumnInfo(name = "mainUrl") val posterUrl: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = true
)