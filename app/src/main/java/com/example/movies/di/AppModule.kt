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
object AppModule {


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

//
//    @[Provides Singleton]
//    fun provideAppDatabase(context: Context): AppDatabase = Room.databaseBuilder(
//        context = context,
//        AppDatabase::class.java,
//        "favorite_movies.db"
//    ).build()
//
//    @Provides
//    fun provideFavoriteDao(appDatabase: AppDatabase): FavoriteDao = appDatabase.favoriteDao()

}


private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"