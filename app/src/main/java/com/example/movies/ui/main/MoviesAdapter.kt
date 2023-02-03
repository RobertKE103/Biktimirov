package com.example.movies.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.databinding.ItemRvMoviesBinding
import com.example.movies.domain.entity.popular.Film

class MoviesAdapter(context: Context): PagingDataAdapter<Film, MoviesViewHolder>(FilmDiffItemCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(layoutInflater.inflate(R.layout.item_rv_movies, parent, false))
    }
}




class MoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    private val binding = ItemRvMoviesBinding.bind(itemView)

    fun bind(film: Film?){
        with(binding){

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