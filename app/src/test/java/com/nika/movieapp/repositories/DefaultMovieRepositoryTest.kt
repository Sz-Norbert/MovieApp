    package com.nika.movieapp.repositories

    import androidx.lifecycle.LiveData
    import com.nika.movieapp.db.MovieDao
    import com.nika.movieapp.pojo.Movie
    import com.nika.movieapp.retrofit.MovieApi
    import io.mockk.every
    import io.mockk.just
    import io.mockk.mockk
    import io.mockk.runs
    import io.mockk.verify
    import kotlinx.coroutines.test.runTest
    import com.google.common.truth.Truth.assertThat
    import com.nika.movieapp.other.Resource
    import com.nika.movieapp.pojo.MovieResponse
    import io.mockk.coEvery
    import io.mockk.coVerify


    import org.junit.Test
    import retrofit2.Response

    class DefaultMovieRepositoryTest {

        val movieDao: MovieDao = mockk()
        val movieApi = mockk<MovieApi>()
        val searchMovieApi : MovieApi = mockk()


        val subject = DefaultMovieRepository(movieDao, movieApi, searchMovieApi)

        @Test
        fun insertMovie_callsMovieDaoUpsert() = runTest {
            val movie: Movie = mockk()
            coEvery { movieDao.insertMovie(movie) } just runs

            subject.insertMovie(movie)
            coVerify { movieDao.insertMovie(movie) }
        }


        @Test
        fun deleteMovie_removeMovieFromDataBase() = runTest {
            val movie: Movie = mockk()
            coEvery { movieDao.deleteMovie(movie) } just runs
            subject.deleteMovie(movie)
            coVerify(exactly = 1 ){ movieDao.deleteMovie(movie) }
        }

        @Test
        fun observeAllMovies_returnsLiveDataOfListofMovie() = runTest {
            val liveData: LiveData<List<Movie>> = mockk()
            every { movieDao.getAllMovies() } returns liveData
            val result = subject.observeAllMovies()
            assertThat(result).isEqualTo(liveData)
            verify(exactly =1) {movieDao.getAllMovies()}
        }

        @Test
        fun searchMovie_ReturnsResourceTypeMovieResponse() = runTest {

            val searchQuery = "batman"
            val response: Response<MovieResponse> = mockk() {
                every { isSuccessful } returns true
                every { body() } returns mockk()
            }
            coEvery { movieApi.searchMovie(search = searchQuery) } returns response
            val result = subject.searchMovie(searchQuery)
            assertThat(result).isInstanceOf(Resource.Success::class.java)
            coVerify(exactly = 1) { movieApi.searchMovie(search = searchQuery) }
        }


        @Test
        fun getUpcoming_ReturnsResourceTypeMovieResponse() = runTest {

            val response: Response<MovieResponse> = mockk() {
                every { isSuccessful } returns true
                every { body() } returns mockk()
            }
            coEvery { movieApi.upcoming() } returns response
            val result = subject.getUpcoming()
            assertThat(result).isInstanceOf(Resource.Success::class.java)
            coVerify(exactly = 1) { movieApi.upcoming() }
        }


        @Test
        fun getNowPlaying_returnsResourceTypeOfResourceOfMovieResponse()= runTest{
            val response: Response<MovieResponse> = mockk() {
                every { isSuccessful } returns true
                every { body() } returns mockk()
            }
            coEvery { movieApi.nowPlaying() } returns response
            val result = subject.getNowPlaying()
            assertThat(result).isInstanceOf(Resource.Success::class.java)
            coVerify(exactly = 1){ movieApi.nowPlaying() }
        }
        @Test
        fun topReated_ReturnsResourceTypeOfMovieResponse()= runTest {
                val response : Response<MovieResponse> = mockk(){
                every { isSuccessful } returns true
                every { body() } returns mockk()
            }
            coEvery {movieApi.getTopRated()} returns response
            val result= subject.getTopRated()
            assertThat(result).isInstanceOf(Resource.Success::class.java)
            coVerify(exactly = 1) { movieApi.getTopRated()  }
        }

        @Test
        fun getPopular_returnsResourceOfTypeMovieResponse()= runTest {
            val response:Response<MovieResponse> = mockk(){
                every { isSuccessful } returns true
                every { body()  } returns mockk()
            }
            coEvery { movieApi.getPopular() } returns response
            val result= subject.getPopular()
            assertThat(result).isInstanceOf(Resource.Success::class.java)
            coVerify(exactly = 1){movieApi.getPopular()}
        }

        @Test
            fun  safeApiCall_returnsSuccess()= runTest {

                val apiCall: suspend () -> Response<MovieResponse> = mockk()
                val movieResponse:Response<MovieResponse>  = mockk(){
                    every { isSuccessful } returns true
                    every { body() } returns mockk()
                }

                coEvery { apiCall.invoke() } returns movieResponse
                val result=subject.safeApiCall(apiCall)
                assertThat(result).isInstanceOf(Resource.Success::class.java)
                assertThat(result.data).isEqualTo(movieResponse.body())
            coVerify (exactly = 1){ apiCall.invoke() }

        }
        @Test
        fun safeApiCall_returnsErrorFailedToConnect() = runTest {
                val apiCallL: suspend () -> Response<MovieResponse> = mockk()
                val errorResponse :Response<MovieResponse> = mockk(){
                    every { isSuccessful } returns false
                    every { body() } returns mockk()
                }
                coEvery { apiCallL.invoke() } returns errorResponse
                val result = subject.safeApiCall(apiCallL)
                assertThat(result).isInstanceOf(Resource.Error::class.java)
                 coVerify (exactly = 1){ apiCallL.invoke() }
        }

        @Test
        fun safeApiCall_retrunsErrorBodyIsNull()= runTest{
            val apiCall: suspend () -> Response<MovieResponse> = mockk()
            val errorResponse : Response<MovieResponse> = mockk(){
                every { body() } returns null
            }
            coEvery { apiCall.invoke() } returns errorResponse
            val result = subject.safeApiCall(apiCall)
            assertThat(result).isInstanceOf(Resource.Error::class.java)
        }

        @Test
        fun safeApiCall_returnsErrorException()= runTest {
            val apiCall: suspend () -> Response<MovieResponse> = mockk()
            coEvery { apiCall.invoke() } throws  Exception("something went wrong")
            val result = subject.safeApiCall(apiCall)
            assertThat(result).isInstanceOf(Resource.Error::class.java)
            assertThat(result.message).isEqualTo("something went wrong")

        }





    }

