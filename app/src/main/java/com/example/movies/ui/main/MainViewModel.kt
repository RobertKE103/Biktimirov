package com.example.movies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movies.domain.entity.popularAndSearch.Film
import com.example.movies.domain.usecase.AddFavoriteUseCase
import com.example.movies.domain.usecase.DeleteFavoriteUseCase
import com.example.movies.domain.usecase.GetFavoriteUseCase
import com.example.movies.domain.usecase.GetListPopularMoviesUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    private val getListPopularMoviesUseCase: Provider<GetListPopularMoviesUseCase>,
    private val getFavoriteUseCase: Provider<GetFavoriteUseCase>,
    private val addFavoriteUseCase: Provider<AddFavoriteUseCase>,
    private val deleteFavoriteUseCase: Provider<DeleteFavoriteUseCase>,
) : ViewModel() {


    private var newPagingSource: PagingSource<*, *>? = null

    val popularMovies: StateFlow<PagingData<Film>> = newPagingDataFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val _favoriteMovies = MutableStateFlow(emptyList<Film>())
    val favoriteMovies = _favoriteMovies.asStateFlow()

    private fun newPagingDataFlow(): Flow<PagingData<Film>> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            val queryMoviesUseCase = getListPopularMoviesUseCase.get()
            queryMoviesUseCase().also { newPagingSource = it }
        }.flow
            .cachedIn(viewModelScope)
    }


    fun addInFavoriteFilm(film: Film){
        val useCase = addFavoriteUseCase.get()
        viewModelScope.launch {
            useCase(film)
        }
    }

    fun deleteInFavoriteFilm(id: Int){
        val useCase = deleteFavoriteUseCase.get()
        viewModelScope.launch {
            useCase(id)
        }
    }

    private fun getFavoriteFilmsFlow(){
        val useCase = getFavoriteUseCase.get()
        viewModelScope.launch {
            _favoriteMovies.value = useCase.invoke()
        }
    }

}