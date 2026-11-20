package com.searchandcode.improvingcodechallenge.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.searchandcode.improvingcodechallenge.model.Movie
import com.searchandcode.improvingcodechallenge.ui.theme.ImprovingCodeChallengeTheme
import com.searchandcode.improvingcodechallenge.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImprovingCodeChallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShowMovies(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    @Composable
    fun ShowMovies(modifier: Modifier = Modifier) {
        val uiStateScreen by mainViewModel.uiMoviesListState.collectAsStateWithLifecycle()
        var selectedMovie by remember { mutableStateOf<Movie?>(null) }
        var userLang = ""
        var userCont = ""

        ManageDialog(
            onLanguageSelected = {
                userLang = it
                Log.d("DEVELOP", "Selected: $it")
            },
            onContentTypeSelected = {
                userCont = it
                Log.d("DEVELOP", "Selected: $it")
            },
            applyingFilters = {
                mainViewModel.getMoviesFromUserConfiguration(userLang, userCont)
            }
        )

        if (selectedMovie != null) {
            ShowDetails(
                movie = selectedMovie!!,
                modifier = modifier,
                onBackClick = { selectedMovie = null }
            )
        } else {
            Box(modifier = modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(5.dp)
                ) {
                    items(
                        count = uiStateScreen.moviesList.size,
                        key = { index -> uiStateScreen.moviesList[index].id }
                    ) { index ->
                        val movie = uiStateScreen.moviesList[index]
                        MovieItemUI(
                            movie = movie,
                            onMovieClick = { selectedMovie = it }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ManageDialog(
        onLanguageSelected: (String) -> Unit,
        onContentTypeSelected: (String) -> Unit,
        applyingFilters: () -> Unit
        )
    {
        val openAlertDialog = remember { mutableStateOf(true) }

        when {
            openAlertDialog.value -> {
                AlertDialogScreen(
                    onDismissRequest = { openAlertDialog.value =  false},
                    onLanguageSelected = onLanguageSelected,
                    onContentTypeSelected = onContentTypeSelected,
                    applyingFilters = applyingFilters
                )
            }
        }
    }

    @Preview(showBackground = true, device = "id:pixel_10")
    @Composable
    fun ShowMoviesPreview() {
        ImprovingCodeChallengeTheme {
            ShowMovies()
        }
    }
}