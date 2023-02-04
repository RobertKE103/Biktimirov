package com.example.movies.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.domain.entity.popularAndSearch.Film
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class MoviesApiPageSource @AssistedInject constructor(
    private val moviesService: MoviesService,
    @Assisted("type") private val type: Int,
    @Assisted("query") private val query: String
) : PagingSource<Int, Film>() {
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: INITIAL_PAGE_NUMBER
        val pageSize = params.loadSize
        return try {
            val response = if (type == POPULAR_TYPE) {
                moviesService.getListPopular()
            } else {
                moviesService.getFromSearchMovies(query)
            }

            return if (response.isSuccessful) {
                val films = checkNotNull(response.body()?.films)
                val nextKey = if (films.size == pageSize) null else page + 1
                val prevKey = if (page == INITIAL_PAGE_NUMBER) null else page - 1
                LoadResult.Page(films, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("type") type: Int,
            @Assisted("query") query: String? = null
        ): MoviesApiPageSource
    }

    companion object {

        const val INITIAL_PAGE_NUMBER = 1
        const val SEARCH_TYPE = 100
        const val POPULAR_TYPE = 101
    }

}