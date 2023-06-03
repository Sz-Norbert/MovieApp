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

    companion object{
        @Volatile
        var INSTANCE :MovieDataBase?=null


        @Synchronized
        fun getInstance(context: Context):MovieDataBase{

            if (INSTANCE==null){
                INSTANCE= Room.databaseBuilder(
                    context,
                    MovieDataBase::class.java,
                    "movie.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MovieDataBase
        }
    }


}