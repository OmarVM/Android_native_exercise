package com.searchandcode.improvingcodechallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.searchandcode.improvingcodechallenge.model.Movie
import com.searchandcode.improvingcodechallenge.usecase.GetPopularMoviesListUseCase
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
    private val getPopularMoviesListUseCase: GetPopularMoviesListUseCase
): ViewModel() {

    private val _uImoviesListState = MutableStateFlow(UiMoviesListState(emptyList()))
    val uiMoviesListState = _uImoviesListState
        .onStart { getPopularMovies() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UiMoviesListState(emptyList()))


    private fun getPopularMovies() {
        viewModelScope.launch {
            val movieList = getPopularMoviesListUseCase.getList()
            if (movieList.isNotEmpty()) {
                _uImoviesListState.update {
                    it.copy(
                        moviesList = movieList
                    )
                }
            }
        }
    }
}