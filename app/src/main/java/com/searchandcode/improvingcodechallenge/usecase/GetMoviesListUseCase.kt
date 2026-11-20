package com.searchandcode.improvingcodechallenge.usecase

import com.searchandcode.improvingcodechallenge.model.Movie
import com.searchandcode.improvingcodechallenge.repository.remote.RemoteRepositoryContract
import javax.inject.Inject

class GetMoviesListUseCase @Inject constructor(
    private val remoteRepository: RemoteRepositoryContract
) {

    suspend fun getList(language: String, contentType: String): List<Movie> {
        when (contentType) {
            "popular" -> return remoteRepository.getPopularMovies(language)
            "top_rated" -> return remoteRepository.getTopRateMovies(language)
        }
        return remoteRepository.getPopularMovies(language)
    }
}