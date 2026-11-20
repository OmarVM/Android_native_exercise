package com.searchandcode.improvingcodechallenge.repository.remote

import retrofit2.Response
import retrofit2.http.GET

interface TheMovieDBAPI {

    @GET("3/movie/popular?language=en-US&page=1")
    suspend fun getPopularMovies(): Response<TMDBPopularResponse>
}