package com.example.movies.data.mappers

import com.example.movies.data.database.models.CountryDb
import com.example.movies.data.database.models.FilmDb
import com.example.movies.data.database.models.GenreDb
import com.example.movies.domain.entity.details.Country
import com.example.movies.domain.entity.details.Genre
import com.example.movies.domain.entity.popularAndSearch.Film


fun FilmDb.toFilm(): Film {
    return Film(
        filmId = filmId,
        genres = listOf(Genre(genres)),
        nameRu = nameRu,
        posterUrlPreview = posterUrl,
        year = year,
        countries = listOf()
    )
}


fun Film.toFilmDb(): FilmDb {
    return FilmDb(
        filmId = filmId,
        genres = genres[0].genre,
        nameRu = nameRu,
        posterUrl = posterUrlPreview,
        year = year
    )
}


fun CountryDb.toCountry(): Country {
    return Country(country)
}

fun GenreDb.toGenre(): Genre = Genre(genre)


fun Country.toCountryDb(): CountryDb {
    return CountryDb(country)
}

fun Genre.toGenreDb(): GenreDb = GenreDb(genre)