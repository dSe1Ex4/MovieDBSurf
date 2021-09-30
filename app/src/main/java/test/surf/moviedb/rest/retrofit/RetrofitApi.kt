package test.surf.moviedb.rest.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import test.surf.moviedb.rest.api.MovieApi
import test.surf.moviedb.rest.dto.MoviePageDTO

interface RetrofitApi : MovieApi {
    @GET("discover/movie")
    override suspend fun getMoviePage(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : MoviePageDTO
}