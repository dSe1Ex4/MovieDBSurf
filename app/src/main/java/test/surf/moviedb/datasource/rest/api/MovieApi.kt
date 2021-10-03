package test.surf.moviedb.datasource.rest.api

import test.surf.moviedb.datasource.rest.dto.MoviePageDTO

/**
 * The Movie DB API 3 - Movie Discover
 *
 * https://developers.themoviedb.org/3/discover/movie-discover
 */
interface MovieDiscoverApi {
    /**
     * @param apiKey Required api key
     * @param language Specify a language to query translatable fields with. (en-US)
     * @param page Specify the page of results to query. (1)
     */
    suspend fun getDiscoverMoviePage(apiKey: String, language: String, page: Int) : MoviePageDTO
}


/**
 * The Movie DB API 3 - Search Movies
 *
 * https://developers.themoviedb.org/3/search/search-movies
 */
interface MovieSearchApi {
    /**
     * @param apiKey Required api key
     * @param language Specify a language to query translatable fields with. (en-US)
     * @param query Required text query to search
     * @param page Specify the page of results to query. (1)
     */
    suspend fun getMoviePageByQuery(apiKey: String, language: String, query: String, page: Int) : MoviePageDTO
}