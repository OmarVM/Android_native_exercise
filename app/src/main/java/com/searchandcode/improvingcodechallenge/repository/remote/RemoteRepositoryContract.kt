package com.searchandcode.improvingcodechallenge.repository.remote

import com.searchandcode.improvingcodechallenge.model.Movie

interface RemoteRepositoryContract {

    suspend fun getPopularMovies(language: String): List<Movie>

    suspend fun getTopRateMovies(language: String): List<Movie>
}