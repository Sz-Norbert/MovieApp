package com.nika.movieapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nika.movieapp.pojo.Movie

@Database(entities = [Movie::class] , version = 1)
@TypeConverters(MovieTypeConverter::class)
abstract class MovieDataBase:RoomDatabase() {
    abstract fun movieDao():MovieDao
}