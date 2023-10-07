package com.nika.movieapp.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.nika.movieapp.other.Resource
import com.nika.movieapp.pojo.Movie
import com.nika.movieapp.pojo.MovieResponse
import com.nika.movieapp.repositories.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MvvmTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    var repository : MovieRepository= mockk(relaxed = true,relaxUnitFun = true){
        coEvery { deleteMovie(any()) } just  runs
    }


    val subject=Mvvm(repository)
    private val dispatcher = StandardTestDispatcher()


    @Before
    fun setUp(){
            Dispatchers.setMain(dispatcher)
    }

    @Test
    fun testExecuteCall_updateUpcomingLiveData() = runTest {
        val movieResponse: MovieResponse = mockk()
        val resource: Resource<MovieResponse> = Resource.Success(movieResponse)
        coEvery { repository.getUpcoming() } returns resource
         subject.executeCall()
        assertThat(subject.upComingLiveData.value == resource)

    }
    @Test
    fun executeCAll_updatePopularLiveData()= runTest{
        val movieResponse : MovieResponse = mockk()
        val resource : Resource<MovieResponse> = Resource.Success(movieResponse)
        coEvery { repository.getPopular() } returns  resource
        subject.executeCall()
        assertThat(subject.popularMovieLiveData.value == resource)
    }

    @Test
    fun executeCall_updateTopRatedLiveData() = runTest {
        val movieResponse: MovieResponse = mockk()
        val topRatedResource : Resource<MovieResponse> = Resource.Success(movieResponse)
        coEvery { repository.getTopRated() } returns topRatedResource
        subject.executeCall()
        assertThat(subject.topRatedMoveLiveData.value == topRatedResource)
    }
    @Test()
    fun  executeCall_uptadeNowPlayingLiveData() = runTest{
        val movieResponse : MovieResponse = mockk()
        val nowPlayingResource : Resource<MovieResponse> = Resource.Success(movieResponse)
        coEvery { repository.getNowPlaying() } returns nowPlayingResource
        subject.executeCall()
        assertThat(subject.nowPlayingLiveData.value == nowPlayingResource)
    }

    @Test
    fun insertMovie_callInsertMovieFunction() = runTest {
        val movie: Movie = mockk()
        coEvery { repository.insertMovie(movie) } just runs
        subject.insertMovie(movie)
        coVerify(exactly = 1){ repository.insertMovie(movie) }
    }

    @Test
    fun deleteMovie_callDeleteMovieFunction() = runTest {
        val movie: Movie = mockk()
        coEvery { repository.deleteMovie(eq(movie)) } just runs
        subject.deleteMovie(movie)
        coVerify(exactly = 1) { repository.deleteMovie(eq(movie)) }
    }
   @Test
   fun getSearchedMovie_updateLiveData()= runTest {
       val movieResponse : MovieResponse = mockk()
       val searchQuery ="batman"
       val movieResource : Resource<MovieResponse> = Resource.Success(movieResponse)
       coEvery { repository.searchMovie(any()) } returns movieResource
       subject.getSearchMovie(searchQuery)
       assertThat(subject.searchMoveLiveData.value == movieResource)
   }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}