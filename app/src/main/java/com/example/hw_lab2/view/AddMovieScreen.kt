package com.example.hw_lab2.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.hw_lab2.enums.Genre
import com.example.hw_lab2.model.MovieData
import com.example.hw_lab2.viewmodel.ListViewModel
import java.time.Year

@Composable
fun AddMovieScreen(navController: NavController, listViewModel: ListViewModel) {
    var title by rememberSaveable { mutableStateOf("") }
    var genre by rememberSaveable { mutableStateOf(Genre.ANIMATION) }
    var directors by rememberSaveable { mutableStateOf(listOf<String>()) }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    var releaseYear by rememberSaveable { mutableStateOf(Year.now()) }

    val saveMovie: () -> Unit = {
        val movie = MovieData(
            title = title,
            genre = genre,
            directors = directors,
            imageUrl = imageUrl.takeIf { it.isNotEmpty() },
            releaseYear = releaseYear
        )
        listViewModel.addMovie(movie)
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
}
