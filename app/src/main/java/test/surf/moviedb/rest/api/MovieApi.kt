package test.surf.moviedb.rest.api

import test.surf.moviedb.rest.dto.MoviePageDTO

/**
 * The Movie DB API 3 - Movie Discover
 *
 * https://developers.themoviedb.org/3/discover/movie-discover
 */
interface MovieApi {
    /**
     * @param apiKey Required api key
     * @param language Specify a language to query translatable fields with. (en-US)
     * @param page Specify the page of results to query. (1)
     */
    suspend fun getMoviePage(apiKey: String, language: String, page: Int) : MoviePageDTO
}