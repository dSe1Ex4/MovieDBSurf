/**
 * Build Config Fields
 */
object BuildFields {
    private val THEMOVIEDB_BASE_URL = BuildField("TMDB_BASE_URL", "https://api.themoviedb.org/3/")

    object RELEASE {
        val ACTUAL_FIELDS = arrayOf(THEMOVIEDB_BASE_URL)
    }
    object DEBUG {
        val ACTUAL_FIELDS = arrayOf(THEMOVIEDB_BASE_URL)
    }
}