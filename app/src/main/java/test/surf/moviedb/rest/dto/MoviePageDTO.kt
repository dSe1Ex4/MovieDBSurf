package test.surf.moviedb.rest.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO Movie Page  (The Movie DB API 3)
 *
 * https://developers.themoviedb.org/3/discover/movie-discover
 */
data class MoviePageDTO(
    @SerializedName("page") val page : Int,
    @SerializedName("results") val results : List<MovieDTO>,
    @SerializedName("total_results") val totalResults : Int,
    @SerializedName("total_pages") val totalPages : Int
)
