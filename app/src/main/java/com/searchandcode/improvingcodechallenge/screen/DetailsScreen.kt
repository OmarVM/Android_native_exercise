package com.searchandcode.improvingcodechallenge.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.searchandcode.improvingcodechallenge.R
import com.searchandcode.improvingcodechallenge.model.Movie
import com.searchandcode.improvingcodechallenge.repository.remote.EndPoints

@Preview(showBackground = true, device = "id:pixel_10")
@Composable
fun ShowDetailsPreview() {
    val movie = Movie(
        id = 1368337,
        title = "The Odyssey",
        overview = "Odysseus, the legendary King of Ithaca, embarks on a long and perilous journey home following the Trojan War. Throughout his voyage, he is forced to confront the whims of gods, mythological monsters, and trials that stretch both his cunning and his humanity to the breaking point.",
        poster_path = "/5rhTDKUhPYvpdQIijFIs5VoWsON.jpg",
        release_date = "2026-07-15"
    )
    ShowDetails(movie = movie)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetails(
    movie: Movie,
    modifier: Modifier = Modifier,
    image: Painter? = null,
    onBackClick: (() -> Unit)? = null
) {
    if (onBackClick != null) {
        BackHandler { onBackClick() }
    }

    val sizeResolver = rememberConstraintsSizeResolver()
    val painterImage = image ?: rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("${EndPoints.IMAGE_W185_URL}${movie.poster_path}")
            .crossfade(true)
            .size(sizeResolver)
            .build(),
        placeholder = painterResource(R.drawable.movie_img_poster)
    )

    Scaffold(
        topBar = {
            if (onBackClick != null) {
                TopAppBar(
                    title = { Text(text = movie.title,
                        textAlign = TextAlign.Center) },
                    navigationIcon = {
                        TextButton(onClick = onBackClick) {
                            Text(text = "Back")
                        }
                    }
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color(0xFFE5DCDC))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterImage,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .then(sizeResolver)
                        .width(310.dp)
                        .padding(10.dp)
                )
                Text(text = "Release date: ${movie.release_date}",
                    style = TextStyle(
                        fontSize = 25.sp
                    ),
                    modifier = Modifier.padding(10.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(text = movie.overview,
                    style = TextStyle(
                        fontSize = 17.sp
                    ),
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}