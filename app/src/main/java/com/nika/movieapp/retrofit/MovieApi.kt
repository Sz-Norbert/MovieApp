package com.nika.movieapp.retrofit

import com.nika.movieapp.pojo.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey:String):Call<MovieResponse>


    @GET("now_playing")
    fun getNowPlayingMovie(@Query("api_key") apiKey: String):Call<MovieResponse>

    @GET("popular")
    fun getPopularMovie(@Query("api_key") apiKey: String):Call<MovieResponse>

    @GET("top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String):Call<MovieResponse>

    @GET("movie")
    fun searchMovie(
    @Query("api_key") apiKey: String,
    @Query("query")  search:String
    ):Call<MovieResponse>


}
