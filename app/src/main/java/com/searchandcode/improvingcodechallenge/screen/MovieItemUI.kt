package com.searchandcode.improvingcodechallenge.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.searchandcode.improvingcodechallenge.R
import com.searchandcode.improvingcodechallenge.model.Movie
import com.searchandcode.improvingcodechallenge.repository.remote.EndPoints


@Preview(showBackground = true, device = "id:pixel_10")
@Composable
fun MovieItemUIPreview() {
    val movie = Movie(
        id = 1368337,
        title = "The Odyssey",
        overview = "Odysseus, the legendary King of Ithaca, embarks on a long and perilous journey home following the Trojan War. Throughout his voyage, he is forced to confront the whims of gods, mythological monsters, and trials that stretch both his cunning and his humanity to the breaking point.",
        poster_path = "/5rhTDKUhPYvpdQIijFIs5VoWsON.jpg",
        release_date = "2026-07-15"
    )
    MovieItemUI(movie)
}

@Composable
fun MovieItemUI(movie: Movie, modifier: Modifier = Modifier) {
    val sizeResolver = rememberConstraintsSizeResolver()
    val painterImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("${EndPoints.IMAGE_W185_URL}${movie.poster_path}")
            .crossfade(true)
            .size(sizeResolver)
            .build(),
        placeholder = painterResource(R.drawable.movie_img_poster)
    )

    Box(modifier = modifier.height(350.dp)) {
        ElevatedCard(modifier = modifier.padding(5.dp)) {
            Image(
                painter = painterImage,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .then(sizeResolver)
                    .fillMaxWidth()
                    .height(500.dp)
                    .clickable {}
            )
        }
    }
}