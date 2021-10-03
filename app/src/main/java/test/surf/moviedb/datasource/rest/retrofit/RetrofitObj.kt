package test.surf.moviedb.datasource.rest.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import test.surf.moviedb.BuildConfig

object RetrofitObj {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.TMDB_BASE_URL)
        .build()

    fun createMovieApi() : MovieApi = retrofit.create(MovieApi::class.java)
}