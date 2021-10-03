package test.surf.moviedb.service.movie

import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query
import test.surf.moviedb.model.Movie

interface IMovieService {
    fun getMoviesData(page: Int): Flow<List<Movie>>
    fun getMoviesDataByQuery(query: String, page: Int): Flow<List<Movie>>

    fun changeMovieFavorite(movieId: Int, isFavorite: Boolean)

    fun saveCacheNow()
}