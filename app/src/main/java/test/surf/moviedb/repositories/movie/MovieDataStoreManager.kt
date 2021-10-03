package test.surf.moviedb.repositories.movie

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

const val DATA_STORE_NAME = "moviesDS"
private val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)

@Singleton
/**
 * Local data saving class for movie
 *
 * !!!Do not use when working with a large amount of data
 * In that case, you should use ROOM e.c.
 */

class MovieDataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {
    private val dataStore = appContext.dataStore

    suspend fun setFavoriteIds(ids: Set<Int>) {
        dataStore.edit { store ->
            store[PREF_KEY_FAVORITES_IDS] = ids.map { it.toString() }.toSet()
        }
    }

    val favoriteIds: Flow<Set<Int>>
        get() = dataStore.data.map { preferences -> preferences[PREF_KEY_FAVORITES_IDS] ?: setOf() }
            .map { set -> set.map { it.toInt() }.toSet() }

    companion object{
        val PREF_KEY_FAVORITES_IDS = stringSetPreferencesKey("fav_movies_ids")
    }
}