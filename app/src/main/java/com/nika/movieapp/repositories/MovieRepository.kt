package com.nika.movieapp.repositories//package com.nika.movieapp.repositories

import androidx.lifecycle.LiveData
import com.nika.movieapp.other.Resource
import com.nika.movieapp.pojo.Movie
import com.nika.movieapp.pojo.MovieResponse


interface MovieRepository {

    suspend fun insertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)
    fun observeAllMovies():LiveData<List<Movie>>
    suspend fun  searchMovie(searchQuery:String):Resource<MovieResponse>
    suspend fun getUpcoming() : Resource<MovieResponse>
    suspend fun getNowPlaying():Resource<MovieResponse>
    suspend fun getTopRated():Resource<MovieResponse>
    suspend fun getPopular():Resource<MovieResponse>

}