/**
 * Build Config Fields
 */
object BuildFields {
    private val THEMOVIEDB_API_KEY = BuildField("TheMovieDBApiKey", "6ccd72a2a8fc239b13f209408fc31c33")
    private val THEMOVIEDB_BASE_URL = BuildField("TheMovieDBBaseUrl", "https://api.themoviedb.org/3/")

    object RELEASE {
        val ACTUAL_FIELDS = arrayOf(THEMOVIEDB_API_KEY, THEMOVIEDB_BASE_URL)
    }
    object DEBUG {
        val ACTUAL_FIELDS = arrayOf(THEMOVIEDB_API_KEY, THEMOVIEDB_BASE_URL)
    }
}

class BuildField(val name: String, val value: String, val type: String = "String")