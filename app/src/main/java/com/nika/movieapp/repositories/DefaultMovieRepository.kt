package com.nika.movieapp.repositories

import androidx.lifecycle.LiveData
import com.nika.movieapp.db.MovieDao
import com.nika.movieapp.other.Resource
import com.nika.movieapp.pojo.Movie
import com.nika.movieapp.pojo.MovieResponse
import com.nika.movieapp.retrofit.MovieApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class DefaultMovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    @Named("searchMovieApi") private val searchMovieApi: MovieApi,
    @Named("defaultMovieApi") private val movieApi: MovieApi

):MovieRepository {

    override suspend fun insertMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteMovie(movie)
   }

    override  fun observeAllMovies(): LiveData<List<Movie>> {
        return movieDao.readAllMovies()
    }

    override suspend fun searchMovie(searchQuery: String): Resource<MovieResponse> {
       return safeApiCall {
           searchMovieApi.searchMovie(search = searchQuery)
       }
    }

    override suspend fun getUpcoming(): Resource<MovieResponse> {
        return safeApiCall {
            movieApi.upcoming()
        }
    }



    override suspend fun getNowPlaying(): Resource<MovieResponse> {
        return safeApiCall {
            movieApi.nowPlaying()
        }
    }

    override suspend fun getTopRated(): Resource<MovieResponse> {
        return safeApiCall {
            movieApi.getTopRated()
        }
    }

    override suspend fun getPopular():Resource<MovieResponse> {
        return safeApiCall {
            movieApi.getPopular()
        }
    }

     suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        try {
            val response = apiCall.invoke()

            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.Success(it)
                } ?: return Resource.Error("An unknown error occurred")
            } else {
                return Resource.Error("An unknown error occurred")
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Couldn't reach the server")
        }
    }


}
