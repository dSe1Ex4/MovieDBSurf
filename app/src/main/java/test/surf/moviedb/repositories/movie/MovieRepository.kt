package test.surf.moviedb.repositories.movie

import kotlinx.coroutines.flow.Flow
import test.surf.moviedb.model.Movie

interface MovieRepository {
    fun getMovieList(page: Int): Flow<List<Movie>>
    fun getMovieListByQuery(query: String, page: Int): Flow<List<Movie>>
}