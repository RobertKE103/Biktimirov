package com.example.movies.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.movies.domain.entity.popularAndSearch.Film
import com.example.movies.domain.usecase.DeleteFavoriteUseCase
import com.example.movies.domain.usecase.GetFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
): ViewModel() {

    val favoriteFilms: LiveData<List<Film>> = getFavoriteUseCase.invoke()

    fun deleteFilm(film: Film){
        viewModelScope.launch {
            deleteFavoriteUseCase(film.filmId)
        }
    }
}