package com.searchandcode.improvingcodechallenge.repository.remote

import com.searchandcode.improvingcodechallenge.model.Movie

data class TMDBPopularResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)