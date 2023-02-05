package com.example.movies.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movies.domain.entity.popularAndSearch.Film
import com.example.movies.domain.usecase.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    private val getListPopularMoviesUseCase: Provider<GetListPopularMoviesUseCase>,
    getFavoriteUseCase: Provider<GetFavoriteUseCase>,
    private val addFavoriteUseCase: Provider<AddFavoriteUseCase>,
    private val deleteFavoriteUseCase: Provider<DeleteFavoriteUseCase>,
    checkNetworkUseCase: Provider<CheckNetworkUseCase>,
) : ViewModel() {


    private var newPagingSource: PagingSource<*, *>? = null

    val popularMovies: StateFlow<PagingData<Film>> = newPagingDataFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val networkStatus = checkNetworkUseCase.get().invoke().stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _isVisible = MutableStateFlow(false)
    val isVisible = _isVisible.asStateFlow()

    fun setupVisibleET(isVisibility: Boolean) {
        _isVisible.value = isVisibility
    }

    val favoriteMovies = getFavoriteUseCase.get().invoke()

    private fun newPagingDataFlow(): Flow<PagingData<Film>> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            val queryMoviesUseCase = getListPopularMoviesUseCase.get()
            queryMoviesUseCase().also { newPagingSource = it }
        }.flow
            .cachedIn(viewModelScope)
    }


    fun setupFavoriteFilm(film: Film) {
        val useCase = addFavoriteUseCase.get()
        viewModelScope.launch {
            if (film.isFavorite) {
                deleteFavoriteUseCase.get()(film.filmId)
            }else{
                useCase(film)
            }
        }
    }


}