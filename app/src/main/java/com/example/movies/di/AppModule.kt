package com.example.movies.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movies.BuildConfig
import com.example.movies.data.api.AuthInterceptor
import com.example.movies.data.api.MoviesService
import com.example.movies.data.database.FavoriteDao
import com.example.movies.data.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"