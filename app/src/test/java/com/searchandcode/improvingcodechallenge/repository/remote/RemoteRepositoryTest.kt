package com.searchandcode.improvingcodechallenge.repository.remote

import com.searchandcode.improvingcodechallenge.model.Movie
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class RemoteRepositoryTest {

    private lateinit var mockApi: TheMovieDBAPI
    private lateinit var repository: RemoteRepository

    private val sampleMovie = Movie(
        id = 1,
        title = "Inception",
        overview = "A thief who steals corporate secrets through the use of dream-sharing technology.",
        poster_path = "/inception.jpg",
        release_date = "2010-07-16",
    )

    private val sampleResponse = TMDBPopularResponse(
        page = 1,
        results = listOf(sampleMovie),
        total_pages = 10,
        total_results = 100
    )

    @Before
    fun setUp() {
        mockApi = mock()
        repository = RemoteRepository(mockApi)
    }

    @Test
    fun `getPopularMovies returns list of movies when API call is successful`() = runTest {
        // Given
        val language = "en-US"
        whenever(
            mockApi.getMovies(
                typeContent = EndPoints.Setting.VALUE_POPULAR,
                page = "1",
                language = language
            )
        ).thenReturn(Response.success(sampleResponse))

        // When
        val result = repository.getPopularMovies(language)

        // Then
        assertEquals(1, result.size)
        assertEquals(sampleMovie, result.first())
    }

    @Test
    fun `getPopularMovies returns empty list when API returns HTTP error`() = runTest {
        // Given
        val language = "en-US"
        val errorResponseBody = "Not Found".toResponseBody("application/json".toMediaTypeOrNull())
        whenever(
            mockApi.getMovies(
                typeContent = EndPoints.Setting.VALUE_POPULAR,
                page = "1",
                language = language
            )
        ).thenReturn(Response.error(404, errorResponseBody))

        // When
        val result = repository.getPopularMovies(language)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getPopularMovies returns empty list when API throws exception`() = runTest {
        // Given
        val language = "en-US"
        whenever(
            mockApi.getMovies(
                typeContent = EndPoints.Setting.VALUE_POPULAR,
                page = "1",
                language = language
            )
        ).thenThrow(RuntimeException("Network error"))

        // When
        val result = repository.getPopularMovies(language)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getPopularMovies returns empty list when response body is null`() = runTest {
        // Given
        val language = "en-US"
        whenever(
            mockApi.getMovies(
                typeContent = EndPoints.Setting.VALUE_POPULAR,
                page = "1",
                language = language
            )
        ).thenReturn(Response.success(null))

        // When
        val result = repository.getPopularMovies(language)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getTopRateMovies returns list of movies when API call is successful`() = runTest {
        // Given
        val language = "es-ES"
        whenever(
            mockApi.getMovies(
                typeContent = EndPoints.Setting.VALUE_TOP_RATED,
                page = "1",
                language = language
            )
        ).thenReturn(Response.success(sampleResponse))

        // When
        val result = repository.getTopRateMovies(language)

        // Then
        assertEquals(1, result.size)
        assertEquals(sampleMovie, result.first())
    }

    @Test
    fun `getTopRateMovies returns empty list when API returns HTTP error`() = runTest {
        // Given
        val language = "es-ES"
        val errorResponseBody = "Server Error".toResponseBody("application/json".toMediaTypeOrNull())
        whenever(
            mockApi.getMovies(
                typeContent = EndPoints.Setting.VALUE_TOP_RATED,
                page = "1",
                language = language
            )
        ).thenReturn(Response.error(500, errorResponseBody))

        // When
        val result = repository.getTopRateMovies(language)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getTopRateMovies returns empty list when API throws exception`() = runTest {
        // Given
        val language = "es-ES"
        whenever(
            mockApi.getMovies(
                typeContent = EndPoints.Setting.VALUE_TOP_RATED,
                page = "1",
                language = language
            )
        ).thenThrow(RuntimeException("Connection timeout"))

        // When
        val result = repository.getTopRateMovies(language)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getTopRateMovies returns empty list when response body is null`() = runTest {
        // Given
        val language = "es-ES"
        whenever(
            mockApi.getMovies(
                typeContent = EndPoints.Setting.VALUE_TOP_RATED,
                page = "1",
                language = language
            )
        ).thenReturn(Response.success(null))

        // When
        val result = repository.getTopRateMovies(language)

        // Then
        assertTrue(result.isEmpty())
    }
}
