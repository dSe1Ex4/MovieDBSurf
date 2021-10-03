package test.surf.moviedb.repositories.movie

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import test.surf.moviedb.BuildConfig
import test.surf.moviedb.model.Movie
import test.surf.moviedb.datasource.rest.retrofit.MovieApi
import java.util.*
import javax.inject.Inject

class MovieRepo @Inject constructor(private val api: MovieApi) : MovieRepository {
    private suspend fun getMoviePageDto(page: Int) =
        api.getDiscoverMoviePage(BuildConfig.TMDB_API_KEY, Locale.getDefault().toLanguageTag(), page)

    private suspend fun getMoviePageDto(page: Int, query: String) =
        api.getMoviePageByQuery(BuildConfig.TMDB_API_KEY, Locale.getDefault().toLanguageTag(), query, page)

    override fun getMovieList(page: Int): Flow<List<Movie>> =
        flow {
            val movieList = getMoviePageDto(page).results
                .map { it.toMovieModel() }

            emit(movieList)
        }.flowOn(Dispatchers.IO)

    override fun getMovieListByQuery(query: String, page: Int): Flow<List<Movie>> =
        flow {
            val movieList = getMoviePageDto(page, query).results
                .map { it.toMovieModel() }

            emit(movieList)
        }.flowOn(Dispatchers.IO)
}