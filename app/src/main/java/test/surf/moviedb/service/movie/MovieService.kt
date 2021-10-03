package test.surf.moviedb.service.movie

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import test.surf.moviedb.di.coroutines.IoScope
import test.surf.moviedb.model.Movie
import test.surf.moviedb.repositories.movie.MovieDataStoreManager
import test.surf.moviedb.repositories.movie.MovieRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MovieService @Inject constructor(private val repo: MovieRepository,
                                       @IoScope private val ioScope: CoroutineScope,
                                       private val favDataStore: MovieDataStoreManager) : IMovieService {
    private val locale = Locale.getDefault()

    private var favoritesIds: MutableSet<Int> = mutableSetOf()

    init {
        ioScope.launch {
            favDataStore.favoriteIds.collect {
                favoritesIds = it.toMutableSet()
            }
        }
    }

    private fun movieAdapter(movie: Movie){
        localeDate(movie)
        favoriteStatusAdapt(movie)
    }

    private fun localeDate(movie: Movie){
        if (movie.releaseDate.isNullOrEmpty()){
            return
        }

        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(movie.releaseDate!!)
        val calendar = Calendar.getInstance().apply {
            time = date!!
        }

        date?.let {
            movie.releaseDate = "${calendar.get(Calendar.DAY_OF_MONTH)} " +
                    "${calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)} " +
                    "${calendar.get(Calendar.YEAR)}"
        }
    }

    private fun favoriteStatusAdapt(movie: Movie){
        movie.isFavorite = movie.id in favoritesIds
    }

    private fun saveFavoriteStatus(){
        ioScope.launch {
            favDataStore.setFavoriteIds(favoritesIds)
        }
    }

    override fun getMoviesData(page: Int): Flow<List<Movie>> {
        return repo.getMovieList(page)
            .onEach { list ->
                list.map(this::movieAdapter)
            }
    }

    override fun getMoviesDataByQuery( query: String, page: Int): Flow<List<Movie>> {
        return repo.getMovieListByQuery(query, page)
            .onEach { list ->
                list.map(this::movieAdapter)
            }
    }

    override fun changeMovieFavorite(movieId: Int, isFavorite: Boolean) {
        if (isFavorite){
            favoritesIds += movieId
        } else {
            favoritesIds.remove(movieId)
        }
    }

    override fun saveCacheNow() {
        saveFavoriteStatus()
    }
}