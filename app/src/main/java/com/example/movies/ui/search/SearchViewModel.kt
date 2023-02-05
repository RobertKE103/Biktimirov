package com.example.movies.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.movies.domain.entity.popularAndSearch.Film
import com.example.movies.domain.usecase.GelListSearchMoviesUseCase
import com.example.movies.domain.usecase.GelListSearchMoviesUseCase_Factory
import com.example.movies.domain.usecase.GetListPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Provider

class SearchViewModel @Inject constructor(
    private val getListSearchMoviesUseCase: Provider<GelListSearchMoviesUseCase>
) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private var newPagingSource: PagingSource<*, *>? = null

    val movies: StateFlow<PagingData<Film>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow.cachedIn(viewModelScope) }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun setQuery(query: String){
        _query.tryEmit(query)
    }


    private fun newPager(query: String): Pager<Int, Film> {
        return Pager(PagingConfig(5, enablePlaceholders = false)) {
            val queryMovieUseCase = getListSearchMoviesUseCase.get()
            queryMovieUseCase(query).also { newPagingSource = it }
        }
    }
}