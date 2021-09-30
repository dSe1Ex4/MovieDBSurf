package test.surf.moviedb.rest.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import test.surf.moviedb.BuildConfig

object RetrofitObj {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.TheMovieDBBaseUrl)
        .build()

    fun create() : RetrofitApi = retrofit.create(RetrofitApi::class.java)
}