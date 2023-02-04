package com.example.movies.data.mappers

import com.example.movies.data.database.models.CountryDb
import com.example.movies.data.database.models.FilmDb
import com.example.movies.data.database.models.GenreDb
import com.example.movies.domain.entity.details.Country
import com.example.movies.domain.entity.details.Genre
import com.example.movies.domain.entity.popularAndSearch.Film


fun FilmDb.toFilm(): Film {
    return Film(
        countries = countries.map { it.toCountry() },
        filmId = filmId,
        genres = genres.map { it.toGenre() },
        nameRu = nameRu,
        posterUrlPreview = posterUrl,
        year = year
    )
}


fun Film.toFilmDb(): FilmDb {
    return FilmDb(
        countries = countries.map { it.toCountryDb() },
        filmId = filmId,
        genres = genres.map { it.toGenreDb() },
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