package com.searchandcode.improvingcodechallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.searchandcode.improvingcodechallenge.model.Movie
import com.searchandcode.improvingcodechallenge.usecase.GetMoviesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiMoviesListState(
    val moviesList: List<Movie>
)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMoviesListUseCase: GetMoviesListUseCase
): ViewModel() {

    private val _uIMoviesListState = MutableStateFlow(UiMoviesListState(emptyList()))
    val uiMoviesListState = _uIMoviesListState
        .onStart { getMovies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UiMoviesListState(emptyList()))


    private fun getMovies() {
        viewModelScope.launch {
            val movieList = getMoviesListUseCase.getList("en-US", "popular")
            if (movieList.isNotEmpty()) {
                _uIMoviesListState.update {
                    it.copy(
                        moviesList = movieList
                    )
                }
            }
        }
    }

    fun getMoviesFromUserConfiguration(language: String, contentType: String) {
        val userSettings = getKeyword(language, contentType)
        viewModelScope.launch {
            val movieList = getMoviesListUseCase.getList(userSettings.first, userSettings.second)
            if (movieList.isNotEmpty()) {
                _uIMoviesListState.update {
                    it.copy(
                        moviesList = movieList
                    )
                }
            }
        }
    }

    private fun getKeyword(language: String, contentType: String): Pair<String,String> {
        var keyLanguage = ""
        when(language) {
            "English" -> keyLanguage = "en-US"
            "Español" -> keyLanguage = "es-US"
        }

        var keyContentType = ""
        when(contentType) {
            "Popular" -> keyContentType = "popular"
            "Top Rated" -> keyContentType = "top_rated"
        }

        return Pair(keyLanguage, keyContentType)
    }
}