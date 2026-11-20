package com.searchandcode.improvingcodechallenge.repository.remote

import com.searchandcode.improvingcodechallenge.model.Movie

interface RemoteRepositoryContract {

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getTopRateMovies(): List<Movie>
}