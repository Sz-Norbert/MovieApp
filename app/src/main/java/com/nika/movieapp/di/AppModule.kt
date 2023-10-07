package com.nika.movieapp.di

import android.content.Context
import android.os.Build.VERSION_CODES.BASE
import androidx.room.Room
import com.nika.movieapp.db.MovieDao
import com.nika.movieapp.db.MovieDataBase
import com.nika.movieapp.repositories.DefaultMovieRepository
import com.nika.movieapp.repositories.MovieRepository
import com.nika.movieapp.retrofit.MovieApi
import com.nika.movieapp.viewModel.Mvvm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieItemDatabase(
        @ApplicationContext context: Context
    ): MovieDataBase {
        return Room.databaseBuilder(context, MovieDataBase::class.java, "movie_db")
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(
        dataBase: MovieDataBase
    ): MovieDao {
        return dataBase.movieDao()
    }

    @Singleton
    @Provides
    @Named("defaultMovieApi")
    fun provideMovieApi() : MovieApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .build()
            .create()
    }

    @Singleton
    @Provides
    @Named("searchMovieApi")
    fun provideSearchMovieApi() : MovieApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/search/")
            .build()
            .create()
    }


    @Singleton
    @Provides
    fun provideDefaultMovieRepository(
        dao: MovieDao,
      @Named("searchMovieApi") searchapi: MovieApi,
        @Named("defaultMovieApi") defaulMovieApi:MovieApi

    ): MovieRepository{
        return DefaultMovieRepository(dao,searchapi, defaulMovieApi)
    }

    @Provides
    @Singleton
    fun provideMvvm(movieRepository: DefaultMovieRepository): Mvvm {
        return Mvvm(movieRepository)
        }
}