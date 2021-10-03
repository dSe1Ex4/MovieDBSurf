package test.surf.moviedb.datasource.rest.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import test.surf.moviedb.datasource.rest.api.MovieDiscoverApi
import test.surf.moviedb.datasource.rest.api.MovieSearchApi
import test.surf.moviedb.datasource.rest.dto.MoviePageDTO

interface MovieApi : MovieDiscoverApi, MovieSearchApi {
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