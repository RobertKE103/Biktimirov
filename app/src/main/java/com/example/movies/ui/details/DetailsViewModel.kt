package com.example.movies.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.entity.details.DetailsResponse
import com.example.movies.domain.usecase.GetDetailsMoviesUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val getDetailsMovies: GetDetailsMoviesUseCase
) : ViewModel() {


    private val _film = MutableLiveData<Response<DetailsResponse>?>()
    val film get() = _film

    fun getMovie(moviesId: Int){
        viewModelScope.launch { _film.value = getDetailsMovies(moviesId) }
    }


}