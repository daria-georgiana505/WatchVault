package com.example.hw_lab2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.hw_lab2.R
import com.example.hw_lab2.model.MovieData
import java.util.UUID


@Composable
fun MovieCard(movie: MovieData, onCheckedChange: (UUID) -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(MaterialTheme.shapes.medium),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            SubcomposeAsyncImage(
                model = movie.imageUrl ?: R.drawable.no_image,
                contentDescription = movie.title,
                modifier = Modifier.size(130.dp),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.no_image),
                        contentDescription = "No image"
                    )
                }
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Genre: ${movie.genre}",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Released: ${movie.releaseYear}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (movie.directors.isNotEmpty()) {
                    Text(
                        text = "Directors: ${movie.directors.joinToString(", ")}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Checkbox(
                checked = movie.isWatched,
                onCheckedChange = {
                    onCheckedChange(movie.id)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}
