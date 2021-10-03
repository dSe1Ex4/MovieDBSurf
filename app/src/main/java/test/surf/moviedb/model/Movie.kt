package test.surf.moviedb.model

data class Movie(
    val id: Int,
    val title : String,
    val overview : String,
    val posterPath : String?,
    var releaseDate : String?,

    var isFavorite: Boolean = false
)
