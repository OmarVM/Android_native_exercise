package com.searchandcode.improvingcodechallenge.repository.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBAPI {

    @GET("3/movie/{typeContent}")
    suspend fun getMovies(
        @Path("typeContent") typeContent : String,
        @Query(EndPoints.Setting.KEY_PAGE) page: String,
        @Query(EndPoints.Setting.KEY_LANGUAGE) language : String,
    ): Response<TMDBPopularResponse>
}