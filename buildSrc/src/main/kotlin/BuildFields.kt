/**
 * Build Config Fields
 */
object BuildFields {
    private val THEMOVIEDB_BASE_URL = BuildField("TMDB_BASE_URL", "https://api.themoviedb.org/3/")
    private val THEMOVIEDB_IMG_BASE_URL = BuildField("TMDB_IMG_BASE_URL", "https://image.tmdb.org/t/p/w200")

    object RELEASE {
        val ACTUAL_FIELDS = arrayOf(THEMOVIEDB_BASE_URL, THEMOVIEDB_IMG_BASE_URL)
    }
    object DEBUG {
        val ACTUAL_FIELDS = arrayOf(THEMOVIEDB_BASE_URL, THEMOVIEDB_IMG_BASE_URL)
    }
}