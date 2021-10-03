package test.surf.moviedb.viewmodel.interfaces

import kotlinx.coroutines.flow.Flow
import test.surf.moviedb.model.Movie
import test.surf.moviedb.utils.states.UiState

interface MovieViewModel {
    /**
     * User interface state
     */
    val uiState: Flow<UiState>

    /**
     * Data for display
     */
    val data: List<Movie>

    /**
     * Request data update
     */
    fun refreshData()

    /**
     * Request data on query expression
     *
     * If the query is empty, it returns the initial data
     */
    fun search(query: String)

    fun setMovieIsFavorite(movieId: Int, isFavorite: Boolean)
}