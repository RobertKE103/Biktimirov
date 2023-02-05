package com.example.movies.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.movies.domain.entity.popularAndSearch.Film
import com.example.movies.domain.usecase.GetFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteUseCase
): ViewModel() {

    private val _favoriteFilms = MutableStateFlow(emptyList<Film>())
    val favoriteFilms = _favoriteFilms.asStateFlow()

    fun getFavoriteList(){
        viewModelScope.launch {
            _favoriteFilms.value = getFavoriteUseCase.invoke()
        }
    }


}