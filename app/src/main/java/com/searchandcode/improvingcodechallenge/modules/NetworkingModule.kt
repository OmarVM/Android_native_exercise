package com.searchandcode.improvingcodechallenge.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.searchandcode.improvingcodechallenge.BuildConfig
import com.searchandcode.improvingcodechallenge.repository.remote.EndPoints
import com.searchandcode.improvingcodechallenge.repository.remote.RemoteRepository
import com.searchandcode.improvingcodechallenge.repository.remote.RemoteRepositoryContract
import com.searchandcode.improvingcodechallenge.repository.remote.TheMovieDBAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkingModule {

    @Singleton
    @Provides
    fun getGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun getClientInterceptor() = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestWithAuth = original.newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.MOVIEDB_API_KEY}")
                .header("Accept", "application/json")
                .build()
            chain.proceed(requestWithAuth)
        }.build()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, clientInterceptor: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(EndPoints.BASE_THEMOVIEDB_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(clientInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideTheMovieDBAPI(retrofit: Retrofit): TheMovieDBAPI {
        return retrofit.create(TheMovieDBAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(
        theMovieDBAPI: TheMovieDBAPI
    ): RemoteRepositoryContract {
        return RemoteRepository(theMovieDBAPI)
    }
}