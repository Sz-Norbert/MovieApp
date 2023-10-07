package com.nika.movieapp.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.nika.movieapp.other.Resource
import com.nika.movieapp.pojo.Movie
import com.nika.movieapp.pojo.MovieResponse
import com.nika.movieapp.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class Mvvm @Inject constructor(val movieRepository: MovieRepository) : ViewModel() {


    private val _upcomingLiveData = MutableLiveData<Resource<MovieResponse>>()
    var upComingLiveData: LiveData<Resource<MovieResponse>> = _upcomingLiveData

    private val _nowPlayingLiveData = MutableLiveData<Resource<MovieResponse>>()
    val nowPlayingLiveData: LiveData<Resource<MovieResponse>> = _nowPlayingLiveData

    private var _popularLiveData = MutableLiveData<Resource<MovieResponse>>()
    val popularMovieLiveData: LiveData<Resource<MovieResponse>> = _popularLiveData

    private var _topRatedLiveData = MutableLiveData<Resource<MovieResponse>>()
    val topRatedMoveLiveData: LiveData<Resource<MovieResponse>> = _topRatedLiveData


    val searchMoveLiveData = MutableLiveData<Resource<MovieResponse>>()
    val favoritesLidata = movieRepository.observeAllMovies()

    fun executeCall() = viewModelScope.launch {

        val upcomingResource = movieRepository.getUpcoming()
        _upcomingLiveData.value = upcomingResource
//
        val popularResponse = movieRepository.getPopular()
        _popularLiveData.value = popularResponse

        val topRatedResponse = movieRepository.getTopRated()
        _topRatedLiveData.value = topRatedResponse
        val nowPlayingResponse = movieRepository.getNowPlaying()
        _nowPlayingLiveData.value = nowPlayingResponse
    }

    fun getSearchMovie(searchQuery: String) = viewModelScope.launch {
        val searchMovieRep = movieRepository.searchMovie(searchQuery)
        searchMoveLiveData.postValue(searchMovieRep)

    }


    fun insertMovie(movie: Movie) {
        Log.d("////", "abcd : ${ viewModelScope.isActive} ")

        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            Log.d("////", "abcd : $movie ")
            movieRepository.insertMovie(movie)
        }
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        movieRepository.deleteMovie(movie)
    }

    fun observeFavorties(): LiveData<List<Movie>> {

        return favoritesLidata
    }
}
