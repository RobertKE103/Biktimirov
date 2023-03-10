package com.example.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.database.models.FilmDb

@Database(entities = [FilmDb::class], version = 2, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

}