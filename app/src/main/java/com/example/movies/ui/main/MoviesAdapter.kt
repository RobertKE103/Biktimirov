package com.example.movies.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ItemRvMoviesBinding
import com.example.movies.domain.entity.popularAndSearch.Film

class MoviesAdapter(context: Context) :
    PagingDataAdapter<Film, MoviesViewHolder>(FilmDiffItemCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var listFavorite = listOf<Film>()

    var onMoviesItemLongClickListener: ((Film, Int) -> Unit)? = null
    var onMoviesItemClickListener: ((Film) -> Unit)? = null

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, listFavorite) }
        holder.itemView.setOnClickListener {
            getItem(position)?.let { it1 -> onMoviesItemClickListener?.invoke(it1) }
        }
        holder.itemView.setOnLongClickListener {
            getItem(position)?.let { it1 -> onMoviesItemLongClickListener?.invoke(it1, position) }
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(layoutInflater.inflate(R.layout.item_rv_movies, parent, false))
    }

    fun favoriteList(divs: List<Film>) {
        listFavorite = divs
    }
}


class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemRvMoviesBinding.bind(itemView)

    fun bind(film: Film, listFavorite: List<Film>) {
        with(binding) {
            Glide.with(itemView.context)
                .load(film.posterUrlPreview)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgFilm)

            nameFilm.text = film.nameRu
            genreFilm.text = String.format(
                itemView.context.getString(R.string.genre_and_data),
                film.genres[0].genre, film.year
            )
            isFavorite.visibility = if (film.filmId in listFavorite.map { it.filmId }) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }

}

private object FilmDiffItemCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.filmId == newItem.filmId
    }

}