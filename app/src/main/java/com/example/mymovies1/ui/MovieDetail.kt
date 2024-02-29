package com.example.mymovies1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.mymovies1.R
import com.example.mymovies1.model.Movie
import com.squareup.picasso.Picasso

class MovieDetail : AppCompatActivity() {
    private var movieDetails: Movie? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        movieDetails = intent.getParcelableExtra("movieDetails")

        movieDetails?.let {

            val movieYear: TextView = findViewById(R.id.movieYear)
            val movieRating: TextView = findViewById(R.id.movieRating)
            val movieViews: TextView = findViewById(R.id.movieViews)
            val movieDescription: TextView = findViewById(R.id.movieDescription)
            val movieBackground: ImageView = findViewById(R.id.movieBackground)
            // Load and set the background image using Picasso
            Picasso.get().load("https://image.tmdb.org/t/p/w500${movieDetails!!.backdrop_path}")
                .into(movieBackground)
            // Set movie year, rating, and views
            movieYear.text = " Year : ${movieDetails!!.release_date}"
            movieRating.text = " Rating : ${movieDetails!!.vote_average.toString()}"
            movieViews.text = " Views : ${movieDetails!!.vote_count.toString()}"

            // Set movie description
            movieDescription.text = movieDetails!!.overview
        }

        val btnShare: Button = findViewById(R.id.btnShare)
        btnShare.setOnClickListener {
            shareMovieDetails()
        }
    }

    private fun shareMovieDetails() {
        movieDetails?.let {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Check out this movie: ${it.title}")
                putExtra(Intent.EXTRA_TEXT, "Title: ${it.title}\n" +
                        "Year: ${it.release_date.substring(0, 4)}\n" +
                        "Rating: ${it.vote_average}\n" +
                        "Views: ${it.vote_count}\n")
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }
}