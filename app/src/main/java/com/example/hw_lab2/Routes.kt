package com.example.hw_lab2

import java.util.UUID

object Routes {
    var homeScreen = "home"
    var addMovieScreen = "add"
    var detailsMovieScreen = "details/{movieId}"

    fun detailsMovieScreen(movieId: UUID) = "details/${movieId}"
}