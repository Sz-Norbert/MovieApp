package com.nika.movieapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nika.movieapp.pojo.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertMovie(movie:Movie)

    @Delete
    suspend fun deleteMovie(movie:Movie)

    @Query("SELECT * FROM movieInformation")
    fun getAllMovies():LiveData<List<Movie>>

}