package com.example.hw_lab2.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hw_lab2.Routes
import com.example.hw_lab2.model.MovieData
import java.util.UUID


@Composable
fun ListMovies(navController: NavController, modifier: Modifier = Modifier, listWithMovies: List<MovieData>, onCheckedChange: (UUID) -> Unit, onDelete: (UUID) -> Unit) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Movies",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
        items(
            listWithMovies,
            key = { movie -> movie.id}
        ) { movie ->
            SwipeBox(
                onDelete = { movieId ->
                    onDelete(movieId)
                },
                modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                movieId = movie.id
            ) {
                MovieCard(
                    movie = movie,
                    onCheckedChange = { movieId ->
                        onCheckedChange(movieId)
                    },
                    onClick = {
                        navController.navigate(Routes.detailsMovieScreen(movie.id))
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBox(modifier: Modifier = Modifier, movieId: UUID, onDelete: (UUID) -> Unit, content: @Composable () -> Unit) {
    val swipeState = rememberSwipeToDismissBoxState()

    val icon: ImageVector = Icons.Outlined.Delete
    val alignment: Alignment = Alignment.CenterEnd
    val color: Color = MaterialTheme.colorScheme.errorContainer

    SwipeToDismissBox(
        modifier = modifier
            .animateContentSize()
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium),
        state = swipeState,
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
                    .background(color)
            ) {
                Icon(
                    modifier = Modifier.minimumInteractiveComponentSize(),
                    imageVector = icon, contentDescription = null,
                )
            }
        }
    ) {
        content()
    }

    if (swipeState.currentValue == SwipeToDismissBoxValue.EndToStart || swipeState.currentValue == SwipeToDismissBoxValue.StartToEnd) {
            onDelete(movieId)
    }
}
