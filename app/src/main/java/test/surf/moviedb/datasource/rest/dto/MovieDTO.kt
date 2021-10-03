package test.surf.moviedb.datasource.rest.dto

import com.google.gson.annotations.SerializedName
import test.surf.moviedb.model.Movie

/**
 * DTO Movie (The Movie DB API 3)
 *
 * https://developers.themoviedb.org/3/discover/movie-discover
 */
data class MovieDTO(
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("poster_path") val posterPath : String?,
    @SerializedName("adult") val isAdult : Boolean,
    @SerializedName("overview") val overview : String,
    @SerializedName("release_date") val releaseDate : String?,
    @SerializedName("genre_ids") val genreIds : List<Int>,
    @SerializedName("original_title") val originalTitle : String,
    @SerializedName("original_language") val originalLanguage : String,
    @SerializedName("backdrop_path") val backdropPath : String?,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("vote_count") val voteCount : Int,
    @SerializedName("video") val isVideo : Boolean,
    @SerializedName("vote_average") val voteAverage : Double
){
    fun toMovieModel() : Movie = Movie(id, title, overview, posterPath, releaseDate)
}
