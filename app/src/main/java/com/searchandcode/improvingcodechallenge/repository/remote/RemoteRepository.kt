package com.searchandcode.improvingcodechallenge.repository.remote

import android.util.Log
import com.searchandcode.improvingcodechallenge.model.Movie
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val theMovieDBAPI: TheMovieDBAPI
): RemoteRepositoryContract {

    override suspend fun getPopularMovies(language: String): List<Movie> {
        return try {
            val response = theMovieDBAPI.getMovies(
                typeContent = EndPoints.Setting.VALUE_POPULAR,
                page = "1",
                language = language
            )
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

    override suspend fun getTopRateMovies(language: String): List<Movie> {
        return try {
            val response = theMovieDBAPI.getMovies(
                typeContent = EndPoints.Setting.VALUE_TOP_RATED,
                page = "1",
                language = language
            )
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
}