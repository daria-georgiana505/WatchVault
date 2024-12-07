package com.example.hw_lab2.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.hw_lab2.R
import com.example.hw_lab2.Routes
import com.example.hw_lab2.viewmodel.ListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: ListViewModel) {

    val movieList = viewModel.movieList

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(stringResource(R.string.app_name))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.addMovieScreen)
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Movie")
            }
        }
    ) { innerPadding ->
        ListMovies(
            modifier = Modifier.padding(innerPadding),
            listWithMovies = movieList,
            onCheckedChange = { movieId ->
                viewModel.toggleIsWatched(movieId)
            },
            navController = navController,
            onDelete = { movieId ->
                viewModel.deleteMovie(movieId)
            }
        )
    }
}

