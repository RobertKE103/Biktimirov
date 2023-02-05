package com.example.movies.di

import com.example.movies.BuildConfig
import com.example.movies.data.api.AuthInterceptor
import com.example.movies.data.api.MoviesService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @[Provides Singleton]
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @[Provides Singleton]
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(AuthInterceptor(BuildConfig.MOVIES_API_KEY))
            .build()

    @[Provides Singleton]
    fun provideMoviesService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)


}

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
