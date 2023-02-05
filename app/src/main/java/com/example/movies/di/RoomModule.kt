package com.example.movies.di

import android.content.Context
import androidx.room.Room
import com.example.movies.data.database.FavoriteDao
import com.example.movies.data.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {


    @Provides
    @Singleton
    fun provideAppDatabase(application: Context): MoviesDatabase =
        Room.databaseBuilder(
            application,
            MoviesDatabase::class.java,
            "business_item.db"
        ). build()


    @Provides
    fun provideFavoriteDao(appDatabase: MoviesDatabase): FavoriteDao =
        appDatabase.favoriteDao()
}