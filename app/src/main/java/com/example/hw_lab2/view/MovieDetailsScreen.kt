package com.example.hw_lab2.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hw_lab2.enums.Genre
import com.example.hw_lab2.viewmodel.ListViewModel
import java.time.Year
import java.util.UUID

@Composable
fun MovieDetailsScreen(navController: NavController, movieId: UUID, viewModel: ListViewModel) {
    val movie by viewModel.movie

    LaunchedEffect(movieId) {
        viewModel.loadMovieById(movieId)
    }

    if (movie != null) {
        var title by rememberSaveable { mutableStateOf(movie!!.title ?: "") }
        var genre by rememberSaveable { mutableStateOf(movie!!.genre ?: Genre.ANIMATION) }
        var directors by rememberSaveable { mutableStateOf(movie!!.directors ?: listOf()) }
        var imageUrl by rememberSaveable { mutableStateOf(movie!!.imageUrl ?: "") }
        var releaseYear by rememberSaveable { mutableStateOf(movie!!.releaseYear ?: Year.now()) }

        val saveMovie: () -> Unit = {
            val updatedMovie = movie!!.copy(
                title = title,
                genre = genre,
                directors = directors,
                imageUrl = imageUrl,
                releaseYear = releaseYear
            )
            viewModel.updateMovie(updatedMovie)
            navController.popBackStack()
        }

        MovieForm(
            title = title,
            onTitleChange = { title = it },
            genre = genre,
            onGenreChange = { genre = it },
            directors = directors,
            onDirectorsChange = { directors = it },
            imageUrl = imageUrl,
            onImageUrlChange = { imageUrl = it },
            releaseYear = releaseYear,
            onReleaseYearChange = { releaseYear = it },
            onMovieSaved = saveMovie
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp)
            )
        }
    }
}
