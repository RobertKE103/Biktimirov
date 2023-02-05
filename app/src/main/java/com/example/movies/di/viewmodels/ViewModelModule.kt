package com.example.movies.di.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movies.ui.details.DetailsViewModel
import com.example.movies.ui.favorite.FavoriteViewModel
import com.example.movies.ui.main.MainViewModel
import com.example.movies.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(DetailsViewModel::class)]
    fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(SearchViewModel::class)]
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(FavoriteViewModel::class)]
    fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel



}