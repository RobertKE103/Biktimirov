package com.example.movies.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.movies.domain.entity.details.MainDetails
import com.example.movies.domain.usecase.GetDetailsMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Provider

class DetailsViewModel @Inject constructor(
    private val getDetailsMovies: GetDetailsMoviesUseCase
) : ViewModel() {


    private val _film = MutableLiveData<Response<MainDetails>?>()
    val film get() = _film

    fun getMovie(moviesId: Int){
        viewModelScope.launch { _film.value = getDetailsMovies(moviesId) }
    }


}