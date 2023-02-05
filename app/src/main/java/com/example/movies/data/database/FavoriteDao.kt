package com.example.movies.data.database

import androidx.room.*
import com.example.movies.data.database.models.FilmDb

@Dao
interface FavoriteDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = FilmDb::class)
    suspend fun insertFilm(filmDb: FilmDb)

    @Query("DELETE FROM favorite_movies WHERE id=:filmId")
    suspend fun deleteFilm(filmId: Int)

    @Query("SELECT * FROM favorite_movies")
    suspend fun getAllNews(): List<FilmDb>
    @Query("SELECT EXISTS(SELECT id FROM favorite_movies WHERE id=:filmId)")
    suspend fun checkItemFavorite(filmId: Int): Int

}