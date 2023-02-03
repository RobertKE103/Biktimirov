package com.example.movies.di

import android.content.Context
import com.example.movies.ui.MainActivity
import com.example.movies.app.MoviesApp
import com.example.movies.di.viewmodels.ViewModelModule
import com.example.movies.ui.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, BindModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(moviesApp: MoviesApp): MoviesApp

    fun inject(mainActivity: MainActivity): MainActivity

    fun inject(mainFragment: MainFragment): MainFragment



    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun apiKey(@MoviesApiQualifier apiKey: String): Builder

        fun build(): AppComponent
    }


}

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
@Qualifier
annotation class MoviesApiQualifier
