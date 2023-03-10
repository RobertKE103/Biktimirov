package com.example.movies.di

import android.app.Application
import android.content.Context
import com.example.movies.ui.MainActivity
import com.example.movies.app.MoviesApp
import com.example.movies.di.viewmodels.ViewModelModule
import com.example.movies.ui.details.DetailsFragment
import com.example.movies.ui.favorite.FavoriteFragment
import com.example.movies.ui.main.MainFragment
import com.example.movies.ui.search.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton


@Singleton
@Component(modules = [BindModule::class, ViewModelModule::class, RoomModule::class, RetrofitModule::class])
interface AppComponent {

    fun inject(moviesApp: MoviesApp): MoviesApp
    fun inject(mainActivity: MainActivity): MainActivity
    fun inject(mainFragment: MainFragment): MainFragment
    fun inject(detailsFragment: DetailsFragment): DetailsFragment
    fun inject(searchFragment: SearchFragment): SearchFragment
    fun inject(favoriteFragment: FavoriteFragment): FavoriteFragment



    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }


}

