package test.surf.moviedb.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import test.surf.moviedb.datasource.rest.retrofit.MovieApi
import test.surf.moviedb.datasource.rest.retrofit.RetrofitObj
import test.surf.moviedb.repositories.movie.MovieRepo
import test.surf.moviedb.repositories.movie.MovieRepository
import test.surf.moviedb.service.movie.IMovieService
import test.surf.moviedb.service.movie.MovieService

@InstallIn(SingletonComponent::class)
@Module
abstract class MovieModule{
    @Binds
    abstract fun bindMovieRepository(repo: MovieRepo): MovieRepository

    @Binds
    abstract fun bindMovieService(service: MovieService): IMovieService
}

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule{
    @Provides
    fun provideRetrofitApi(): MovieApi = RetrofitObj.createMovieApi()
}

