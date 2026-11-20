package com.searchandcode.improvingcodechallenge.repository.remote

import android.util.Log
import com.searchandcode.improvingcodechallenge.model.Movie
import javax.inject.Inject

class RemoteRepository @Inject constructor(
private val theMovieDBAPI: TheMovieDBAPI
): RemoteRepositoryContract {

    override suspend fun getPopularMovies(): List<Movie> {
        return try {
            val response = theMovieDBAPI.getPopularMovies()
            if (response.isSuccessful) {
                Log.d("DEVELOP", "Total items: ${response.body()?.results?.size}")
                response.body()?.results ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.d("DEVELOP", "Error Message: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getTopRateMovies(): List<Movie> {
        TODO("Not yet implemented")
    }
}