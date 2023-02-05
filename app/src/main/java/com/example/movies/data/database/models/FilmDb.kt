package com.example.movies.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FilmDb(
    @PrimaryKey @ColumnInfo(name = "id") var filmId: Int,
    @ColumnInfo("genre") val genres: String,
    @ColumnInfo(name = "normalNameMovie") val nameRu: String,
    @ColumnInfo(name = "mainUrl") val posterUrl: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = true
)