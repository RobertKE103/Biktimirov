package com.example.movies.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ItemRvMoviesBinding
import com.example.movies.domain.entity.popularAndSearch.Film

class FavoriteAdapter(context: Context): ListAdapter<Film, FavoriteViewHolder>(FilmDiffItemCallback) {


    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(layoutInflater.inflate(R.layout.item_rv_movies, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}



class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val binding = ItemRvMoviesBinding.bind(itemView)

    fun bind(film: Film){
        binding.isFavorite.visibility = View.VISIBLE
        binding.nameFilm.text = film?.nameRu
        binding.genreFilm.text = String.format(
            itemView.context.getString(R.string.genre_and_data),
            film.genres[0].genre, film.year
        )

        Glide.with(itemView.context)
            .load(film.posterUrlPreview)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imgFilm)
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