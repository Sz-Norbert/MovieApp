package com.nika.movieapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nika.movieapp.db.MovieDataBase
import com.nika.movieapp.fragment.HomeFragment
import com.nika.movieapp.pojo.MovieResponse
import com.nika.movieapp.pojo.Movie
import com.nika.movieapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Mvvm(
   val movieDataBase: MovieDataBase
):ViewModel() {

    private var upcomeingLiveData:MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    private var nowPlayingLiveData:MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    private var popularMovieLiveData: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    private var topRatedMoveLiveData: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
    private  var favoritesLiveData:LiveData<List<Movie>> = movieDataBase.movieDao().getAllMovies()
    private var searchMoveLiveData=MutableLiveData<List<Movie>>()

    fun getUpcomeing(){
        RetrofitInstance.api.getUpcomingMovies(HomeFragment.API_KEY).enqueue(object :
            Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {
                if (response.body()!=null){

                    val movieList : List<Movie> = response.body()?.movies ?: emptyList()
                    upcomeingLiveData.value=movieList




                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("HOME FRAGMENT" , t.message.toString())
            }
        })
    }

    fun observeUpcomeingMoveLiveData():LiveData<List<Movie>>{
        return upcomeingLiveData
    }


    fun getNowPlayingMovie(){
        RetrofitInstance.api.getNowPlayingMovie(HomeFragment.API_KEY).enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {

                if (response.body()!=null){
                    val nowPlaying:List<Movie> =response.body()?.movies ?: emptyList()
                    nowPlayingLiveData.value=nowPlaying
                }

            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("HOME FRAGMENT" , t.message.toString())            }
        })
    }


    fun observeNowPlayingMovieLiveData():LiveData<List<Movie>>{
        return nowPlayingLiveData
    }

    fun  getPopularMovie(){
        RetrofitInstance.api.getPopularMovie(HomeFragment.API_KEY).enqueue(object :
            Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {

                if (response.body() !=null){

                    var popularMovies=response.body()?.movies ?: emptyList()
                    popularMovieLiveData.value=popularMovies
                }

            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("HOME FRAGMENT" , t.message.toString())
            }

        })

    }

    fun observePopularMovie():LiveData<List<Movie>>{
        return popularMovieLiveData
    }


    fun getTopRatedMoveis(){
        RetrofitInstance.api.getTopRatedMovies(HomeFragment.API_KEY).enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>,
                response: Response<MovieResponse?>
            ) {

                if (response.body() != null){

                    var topRatedMovies=response.body()?.movies ?: emptyList()
                    topRatedMoveLiveData.value=topRatedMovies
                }
            }

            override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                Log.d("HOME FRAGMENT" , t.message.toString())
            }
        })
    }

    fun observeTopRatedMoviesLiveData():LiveData<List<Movie>>{
        return topRatedMoveLiveData
    }

    fun insertMovie(movie: Movie){


        viewModelScope.launch(Dispatchers.IO) {


            movieDataBase.movieDao().upsertMovie(movie)
        }
    }

    fun deletMovie(movie: Movie){
        viewModelScope.launch {
            movieDataBase.movieDao().deleteMovie(movie)
        }


    }

     fun observeFavorties():LiveData<List<Movie>>{
        return favoritesLiveData
    }

    fun searchMove(search: String) {
        RetrofitInstance.apiSearch.searchMovie(HomeFragment.API_KEY, search)
            .enqueue(object : Callback<MovieResponse?> {
                override fun onResponse(
                    call: Call<MovieResponse?>,
                    response: Response<MovieResponse?>
                ) {
                    if (response.body() != null) {
                        val movieList = response.body()?.movies ?: emptyList()
                        searchMoveLiveData.value = movieList
                    }
                }

                override fun onFailure(call: Call<MovieResponse?>, t: Throwable) {
                    Log.d("HOME FRAGMENT", t.message.toString())
                }
            })
    }

    fun observeSearchedMovieLivedata():LiveData<List<Movie>>{
        return searchMoveLiveData
    }

    fun deleteMovie(movie: Movie){
        viewModelScope.launch {
            movieDataBase.movieDao().deleteMovie(movie)
        }
    }

     }
