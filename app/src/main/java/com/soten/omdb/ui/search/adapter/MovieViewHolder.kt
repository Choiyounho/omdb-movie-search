package com.soten.omdb.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soten.omdb.databinding.ItemMovieBinding
import com.soten.omdb.domain.model.search.MovieModel
import com.soten.omdb.extension.load

class MovieViewHolder(
    private val binding: ItemMovieBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieModel, onItemClicked: (String) -> Unit) {
        binding.moviePoster.load(movie.poster)

        binding.root.setOnClickListener {
            onItemClicked(movie.imdbID)
        }
    }

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            return MovieViewHolder(
                ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}