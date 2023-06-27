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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Mvvm(val movieDataBase: MovieDataBase):ViewModel() {

   var upcomeingLiveData  : MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
     val nowPlayingLiveData:MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
     val popularMovieLiveData: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
     val topRatedMoveLiveData: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
     var favoritesLiveData:LiveData<List<Movie>> = movieDataBase.movieDao().getAllMovies()
     var searchMoveLiveData=MutableLiveData<List<Movie>>()

    fun  executeCall(){
        val apiKey= HomeFragment.API_KEY
       viewModelScope.launch {
           val getUpcommeing= RetrofitInstance.api.getAll("upcoming")
           upcomeingLiveData.value = getUpcommeing.movies

           val getNowPlaying=  RetrofitInstance.api.getAll("now_playing")
           nowPlayingLiveData.value = getNowPlaying.movies

           val getPopularMovie=RetrofitInstance.api.getAll("popular")
           popularMovieLiveData.value = getPopularMovie.movies

           val getTopRated=RetrofitInstance.api.getAll("top_rated")
           topRatedMoveLiveData.value = getTopRated.movies
        }
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

    fun observeFavorties():LiveData<List<Movie>> {
        return favoritesLiveData
    }
     }
