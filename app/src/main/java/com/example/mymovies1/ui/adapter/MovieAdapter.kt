package com.example.mymovies1.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovies1.R
import com.example.mymovies1.model.Movie
import com.example.mymovies1.model.MovieModel
import com.squareup.picasso.Picasso

class MovieAdapter(private val onItemClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateMovies(newMovies: MovieModel) {
        movies = newMovies.results
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.movieTitleTextView)
        private val ratingTextView: TextView = itemView.findViewById(R.id.movieRatingTextView)
        private val languageTextView: TextView = itemView.findViewById(R.id.movieLanguageTextView)
        private val titleImage: ImageView = itemView.findViewById(R.id.moviePosterImageView)
        fun bind(movie: Movie) {
            titleTextView.text = movie.original_title
            ratingTextView.text = movie.vote_average.toString()
            languageTextView.text = movie.original_language

            Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(titleImage)

            itemView.setOnClickListener {
                onItemClick(movie)
            }
        }
    }
}