package test.surf.moviedb.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import test.surf.moviedb.model.Movie
import test.surf.moviedb.viewmodel.interfaces.MovieViewModel
import test.surf.moviedb.utils.states.UiState
import test.surf.moviedb.repositories.movie.MovieRepository
import test.surf.moviedb.service.movie.IMovieService
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieService: IMovieService) : BaseViewModel(), MovieViewModel, SaveableViewModel {
    private val _uiState: MutableSharedFlow<UiState> = MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val uiState: SharedFlow<UiState> = _uiState.asSharedFlow()

    private var lastQuery: String? = null

    override val data = mutableListOf<Movie>()

    init {
        loadPopMovie()
    }

    private fun loadPopMovie(){
        viewModelScope.launch(Dispatchers.IO){
            _uiState.emit(UiState.UPDATING)
            loadMovies()
        }
    }

    private fun loadSearchedMovie(query: String){
        viewModelScope.launch(Dispatchers.IO){
            _uiState.emit(UiState.UPDATING)
            loadMovies(query=query)
        }
    }

    private suspend fun loadMovies(page: Int = 1){
        prepareForLoad()
        lastQuery = null
        var state = UiState.LOADED

        movieService.getMoviesData(page)
            .catch {
                Log.e("MainViewModel", it.stackTraceToString())
                state = UiState.error(it.localizedMessage)
            }
            .collect {
                data.addAll(it)
            }
        _uiState.emit(state)
    }

    private suspend fun loadMovies(page: Int = 1, query: String = ""){
        prepareForLoad()
        var state = UiState.FOUND

        movieService.getMoviesDataByQuery(query, page)
            .catch {
                Log.e("MainViewModel", it.stackTraceToString())
                state = UiState.error(it.localizedMessage)
            }
            .collect {
                data.addAll(it)
            }
        _uiState.emit(state)
    }

    private suspend fun refreshMovies(lastQuery: String?){
        if (lastQuery.isNullOrEmpty()){
            loadMovies()
        } else {
            loadSearchedMovie(lastQuery)
        }

    }

    /**
     * Используется для подготовки контейнера к обновлению
     *
     * При использовании пагинации, следует изменить
     */
    private fun prepareForLoad(){
        data.clear()
    }

    override fun search(query: String) {
        if (query.isNotEmpty()){
            lastQuery = query

            loadSearchedMovie(query)
        } else {
            loadPopMovie()
        }
    }

    override fun setMovieIsFavorite(movieId: Int, isFavorite: Boolean) {
        movieService.changeMovieFavorite(movieId, isFavorite)
    }

    override fun refreshData() {
        viewModelScope.launch(Dispatchers.IO){
            _uiState.emit(UiState.UPDATING)
            refreshMovies(lastQuery)
        }
    }

    override fun onSave(bundle: Bundle) {
        movieService.saveCacheNow()
    }

    override fun onRestore(bundle: Bundle) {

    }
}