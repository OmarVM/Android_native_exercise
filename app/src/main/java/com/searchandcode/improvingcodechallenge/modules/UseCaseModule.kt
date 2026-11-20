package com.searchandcode.improvingcodechallenge.modules

import com.searchandcode.improvingcodechallenge.repository.remote.RemoteRepositoryContract
import com.searchandcode.improvingcodechallenge.usecase.GetMoviesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun providePopularMoviesUseCase(
        repository: RemoteRepositoryContract
    ): GetMoviesListUseCase {
        return GetMoviesListUseCase(repository)
    }
}