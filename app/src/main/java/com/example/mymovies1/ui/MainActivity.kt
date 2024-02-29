package com.example.mymovies1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies1.R
import com.example.mymovies1.model.Movie
import com.example.mymovies1.service.RetrofitClient
import com.example.mymovies1.ui.adapter.MovieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieAdapter = MovieAdapter { movie ->
            val intent = Intent(this, MovieDetail::class.java).apply {
                putExtra("movieDetails", movie)
            }
            startActivity(intent)
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter

        val unitsRadioGroup: RadioGroup = findViewById(R.id.units_rg)
        unitsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            if (radioButton?.isChecked == true) {
                when (checkedId) {
                    R.id.metric_unit_rb -> fetchPopularMovies()
                    R.id.us_unit_rb -> fetchTopRatedMovies()
                }
            }
        }


        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        lifecycleScope.launch {
            val popularMovies = withContext(Dispatchers.IO) {
                RetrofitClient.movieApiService.getPopularMovies()
            }
            movieAdapter.updateMovies(popularMovies)
        }
    }

    private fun fetchTopRatedMovies() {
        lifecycleScope.launch {
            val topRatedMovies = withContext(Dispatchers.IO) {
                RetrofitClient.movieApiService.getTopRatedMovies()
            }
            movieAdapter.updateMovies(topRatedMovies)
        }
    }
}