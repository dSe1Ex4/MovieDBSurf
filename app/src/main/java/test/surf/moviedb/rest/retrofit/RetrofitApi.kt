package test.surf.moviedb.rest.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import test.surf.moviedb.rest.api.MovieDiscoverApi
import test.surf.moviedb.rest.api.MovieSearchApi
import test.surf.moviedb.rest.dto.MoviePageDTO

interface RetrofitApi : MovieDiscoverApi, MovieSearchApi {
    @GET("discover/movie")
    override suspend fun getDiscoverMoviePage(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : MoviePageDTO

    @GET("search/movie")
    override suspend fun getMoviePageByQuery(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MoviePageDTO
}