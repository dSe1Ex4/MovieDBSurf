import java.io.File

/**
 * Simple API Keys BuildField Fielder for OpenSource Repos without val-safe
 */
class ApiKeysFielder(projectDir: String) {
    private val apiKeys = File("$projectDir${File.separator}$KEYS_FILENAME").readLines()

    companion object{
        const val KEYS_FILENAME = "apikeys.txt"
        const val TMDB_RELEASE_KEY_NUM = 0
        const val TMDB_DEBUG_KEY_NUM = 1
    }

    val releaseBuildFields = listOf(
        BuildField("TMDB_API_KEY", apiKeys[TMDB_RELEASE_KEY_NUM])
    )

    val debugBuildFields = listOf(
        BuildField("TMDB_API_KEY", if (apiKeys.size >= 2) apiKeys[TMDB_DEBUG_KEY_NUM] else apiKeys[TMDB_RELEASE_KEY_NUM])
    )
}