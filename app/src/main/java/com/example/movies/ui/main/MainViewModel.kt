package com.example.movies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movies.domain.entity.popular.Film
import com.example.movies.domain.usecase.GetListPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.coroutineContext

class MainViewModel @Inject constructor(
    private val getListPopularMoviesUseCase: Provider<GetListPopularMoviesUseCase>,
) : ViewModel() {


    private var newPagingSource: PagingSource<*, *>? = null

    val popularMovies: StateFlow<PagingData<Film>> = newPagingDataFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPagingDataFlow(): Flow<PagingData<Film>> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            val queryMoviesUseCase = getListPopularMoviesUseCase.get()
            queryMoviesUseCase().also { newPagingSource = it }
        }.flow
            .cachedIn(viewModelScope)
    }

}