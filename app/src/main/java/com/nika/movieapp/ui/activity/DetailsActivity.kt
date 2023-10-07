package com.nika.movieapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nika.movieapp.databinding.ActivityDetailsBinding
import com.nika.movieapp.other.Util.Companion.TAG
import com.nika.movieapp.ui.fragment.HomeFragment
import com.nika.movieapp.pojo.Movie
import com.nika.movieapp.viewModel.Mvvm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailsBinding
    lateinit var movieId:String
    lateinit var relaseData:String
    lateinit var movieTitle:String
    lateinit var movieImg:String
    lateinit var details:String
    lateinit var extras : Intent
    private val viewModel: Mvvm by viewModels()
    var movie: Movie?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        extras=intent

        getMovieInfo()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnSave.setOnClickListener {
            try {
                if (movie != null) {
                    viewModel.insertMovie(movie!!)
                    Log.d(TAG, "Movie inserted: ${movie!!.title}")
                    Toast.makeText(
                        this,
                        "${movie!!.title} has been added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error inserting movie: ${e.message}", e)
                Toast.makeText(this, "Error inserting movie: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


                        private fun getMovieInfo() {
            var bundle: Bundle? = intent.extras
        if (bundle !=null) {
            val json=bundle.getString(HomeFragment.JSON_KEY)
             movie=Gson().fromJson(json, Movie::class.java)
            movieId= movie?.id.toString()
            movieImg = movie?.backdropPath ?: ""
            movieTitle = movie?.title ?: ""
            details = movie?.overview ?: ""
            relaseData = movie?.releaseDate ?: ""
            Glide.with(this)
                .load(HomeFragment.IMAGE_BASE+ movieImg)
                .into(binding.imgMovie)
            binding.collapsingToolbar.title=movieTitle
            binding.tvDetalies.text=details
            binding.tvRelase.text=relaseData


        }
    }
}