package com.nika.movieapp.retrofit

import com.nika.movieapp.fragment.HomeFragment
import com.nika.movieapp.pojo.Movie
import com.nika.movieapp.pojo.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {




 @GET("{type}?api_key=${HomeFragment.API_KEY} ")
  fun getAll(@Path("type") type:String ):Response<Movie>


    @GET("movie")
    fun searchMovie(
    @Query("api_key") apiKey: String,
    @Query("query")  search:String
    ):Response<Movie>


}
