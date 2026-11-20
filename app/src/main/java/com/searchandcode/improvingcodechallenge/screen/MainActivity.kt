package com.searchandcode.improvingcodechallenge.screen

import android.os.Bundle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

        Box(modifier = modifier.fillMaxSize()){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(5.dp)
            ) {
                items(uiStateScreen.moviesList.size) { index ->
                    MovieItemUI(
                        movie = uiStateScreen.moviesList[index]
                    )
                }
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