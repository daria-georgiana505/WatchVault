package com.example.hw_lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hw_lab2.model.MovieRepository
import com.example.hw_lab2.ui.theme.HW_lab2Theme
import com.example.hw_lab2.view.AddMovieScreen
import com.example.hw_lab2.view.HomeScreen
import com.example.hw_lab2.view.MovieDetailsScreen
import com.example.hw_lab2.viewmodel.ListViewModel
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val movieRepository = MovieRepository()
        val listViewModel = ListViewModel(movieRepository)

        setContent {
            HW_lab2Theme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = Routes.homeScreen){
                        composable(Routes.homeScreen) {
                            HomeScreen(navController = navController, viewModel = listViewModel, modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding))
                        }
                        composable(Routes.addMovieScreen) {
                            AddMovieScreen(listViewModel = listViewModel, navController = navController)
                        }
                        composable(
                            route = Routes.detailsMovieScreen,
                            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val movieIdString = backStackEntry.arguments?.getString("movieId")
                            val movieId = UUID.fromString(movieIdString)
                            MovieDetailsScreen(
                                navController = navController,
                                movieId = movieId,
                                viewModel = listViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}