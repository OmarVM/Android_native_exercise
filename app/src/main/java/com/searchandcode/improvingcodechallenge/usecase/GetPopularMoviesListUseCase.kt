package com.searchandcode.improvingcodechallenge.usecase

import com.searchandcode.improvingcodechallenge.model.Movie
import com.searchandcode.improvingcodechallenge.repository.remote.RemoteRepositoryContract
import javax.inject.Inject

class GetPopularMoviesListUseCase @Inject constructor(
    private val remoteRepository: RemoteRepositoryContract
) {

    suspend fun getList(): List<Movie> {
        return remoteRepository.getPopularMovies()
    }
}