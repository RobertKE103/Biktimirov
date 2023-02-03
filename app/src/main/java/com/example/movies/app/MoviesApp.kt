package com.example.movies.app

import android.app.Application
import android.content.Context
import com.example.movies.BuildConfig
import com.example.movies.di.AppComponent
import com.example.movies.di.DaggerAppComponent

class MoviesApp : Application() {

    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent
        get() = requireNotNull(_appComponent) {
            "AppComponent must be not null"
        }

    override fun onCreate() {
        super.onCreate()

        _appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()


    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MoviesApp -> appComponent
        else -> (applicationContext as MoviesApp).appComponent
    }