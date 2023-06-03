package com.nika.movieapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.nika.movieapp.databinding.ActivityDetailsBinding
import com.nika.movieapp.db.MovieDataBase
import com.nika.movieapp.fragment.HomeFragment
import com.nika.movieapp.pojo.Movie
import com.nika.movieapp.viewModel.MovieViewModelFactory
import com.nika.movieapp.viewModel.Mvvm

class DetailsActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailsBinding
    lateinit var movieId:String
    lateinit var relaseData:String
    lateinit var movieTitle:String
    lateinit var movieImg:String
    lateinit var details:String
    lateinit var extras : Intent
    lateinit var viewModel: Mvvm
     var movie: Movie?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieDataBase=MovieDataBase.getInstance(this)
        val viewModelFactory=MovieViewModelFactory(movieDataBase)
        viewModel=ViewModelProvider(this,viewModelFactory)[Mvvm::class.java]




        extras=intent

        if (extras != null ) {
            getMovieInfo()
        }


        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnSave.setOnClickListener {

            movie?.let { movie ->
                viewModel.insertMovie(movie)
                Toast.makeText(this,"movie ${movie.title} has saved" , Toast.LENGTH_SHORT).show()


            }

        }
    }



    private fun getMovieInfo() {



            var bundle: Bundle? = intent.extras
        if (bundle !=null) {

            val json=bundle.getString(HomeFragment.JSON_KEY)
             movie=Gson().fromJson(json, Movie::class.java)
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