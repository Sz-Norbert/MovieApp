package com.nika.movieapp.retrofit

import com.nika.movieapp.ui.fragment.HomeFragment
import com.nika.movieapp.pojo.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("upcoming")
    suspend fun upcoming(
        @Query("api_key")
        apiKey: String = HomeFragment.API_KEY): Response<MovieResponse>
    @GET("now_playing")
    suspend fun nowPlaying(
        @Query("api_key")
        apiKey: String = HomeFragment.API_KEY): Response<MovieResponse>


    @GET("popular")
    suspend fun getPopular(
        @Query("api_key")
        apiKey: String = HomeFragment.API_KEY): Response<MovieResponse>
    @GET("top_rated")
    suspend fun getTopRated(
        @Query("api_key")
        apiKey: String = HomeFragment.API_KEY): Response<MovieResponse>
    @GET("movie")
   suspend fun searchMovie(
        @Query("api_key") apiKey: String= HomeFragment.API_KEY,
        @Query("query")  search:String
    ):Response<MovieResponse>


}
