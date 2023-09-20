package com.nika.movieapp.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nika.movieapp.fragment.HomeFragment
import com.nika.movieapp.other.Resource
import com.nika.movieapp.pojo.MovieResponse
import com.nika.movieapp.pojo.Movie
import com.nika.movieapp.repositories.DefaultMovieRepository
import com.nika.movieapp.retrofit.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class Mvvm @Inject constructor( val movieRepository: DefaultMovieRepository):ViewModel() {

   var upComingLiveData  = MutableLiveData<List<Movie>>()
     val nowPlayingLiveData = MutableLiveData<List<Movie>>()
     val popularMovieLiveData = MutableLiveData<List<Movie>>()
     val topRatedMoveLiveData = MutableLiveData<List<Movie>>()
     var favoritesLiveData = movieRepository.observeAllMovies()
     var searchMoveLiveData=MutableLiveData<List<Movie>>()

    fun executeCall() {
        viewModelScope.launch {
            val upcomingResource = movieRepository.getUpcoming()
            if (upcomingResource is Resource.Success) {
                upComingLiveData.postValue(listOf(upcomingResource.data!!))
            } else {
                Log.i(TAG, "executeCall: Upcoming Resource failed")
            }

            val nowPlayingResource = movieRepository.getNowPlaying()
            if (nowPlayingResource is Resource.Success) {
                nowPlayingLiveData.postValue(listOf(nowPlayingResource.data!!))
            } else {
                Log.i(TAG, "executeCall: Now Playing Resource failed")
            }

            val topRatedResource = movieRepository.getTopRated()
            if (topRatedResource is Resource.Success) {
                topRatedMoveLiveData.postValue(listOf(topRatedResource.data!!))
            } else {
                Log.i(TAG, "executeCall: Top Rated Resource failed")
            }

            val popularResource = movieRepository.getPopular()
            if (popularResource is Resource.Success) {
                popularMovieLiveData.postValue(listOf(popularResource.data!!))
            } else {
                Log.i(TAG, "executeCall: Popular Resource failed")
            }

//            val searchResource = movieRepository.searchMovie(apiKey,searchQuery)
//            if (searchResource is Resource.Success) {
//                searchMoveLiveData.postValue(listOf(searchResource.data!!))
//            } else {
//                Log.i(TAG, "executeCall: Search Resource failed")
//            }
        }
    }






    fun insertMovie(movie: Movie){


        viewModelScope.launch(Dispatchers.IO) {

        movieRepository.insertMovie(movie)

        }
    }
    fun deletMovie(movie: Movie){
        viewModelScope.launch {

            movieRepository.deleteMovie(movie)
        }
    }

    fun observeSearchedMovieLivedata():LiveData<List<Movie>>{
        return searchMoveLiveData
    }

    fun observeFavorties():LiveData<List<Movie>> {
        return favoritesLiveData
    }



}

