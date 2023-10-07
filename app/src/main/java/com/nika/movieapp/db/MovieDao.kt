package com.nika.movieapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nika.movieapp.pojo.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movieInformation")
    fun readAllMovies():LiveData<List<Movie>>
}