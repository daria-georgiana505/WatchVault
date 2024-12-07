package com.example.hw_lab2.model

import com.example.hw_lab2.enums.Genre
import kotlinx.coroutines.delay
import java.time.Year
import java.util.UUID

class MovieRepository {
    private var listMovies = mutableListOf(
        MovieData(
            title = "Percy Jackson",
            genre = Genre.FANTASY,
            directors = listOf("Rick", "RIORDAN"),
            imageUrl = "https://i.redd.it/tfld1b9nho3c1.jpg",
            releaseYear = Year.of(2023),
            isWatched = true
            ),
        MovieData(
            title = "Arcane 2",
            genre = Genre.ANIMATION,
            releaseYear = Year.of(2024)
        ),
        MovieData(
            title = "The Last Of Us",
            genre = Genre.FICTION,
            releaseYear = Year.of(2023),
            imageUrl = "https://m.media-amazon.com/images/M/MV5BY2JiNjU3NWYtMTRlYS00NzY3LWE2NDQtZGFkNWE2MDU4OTExXkEyXkFqcGc@._V1_.jpg"
        )
    )

    suspend fun getMovies(): List<MovieData> {
        delay(2000)
        return listMovies
    }

    suspend fun addMovie(movie: MovieData) {
        delay(2000)
        listMovies.add(movie)
    }

    suspend fun getMovieById(id: UUID): MovieData? {
        delay(2000)
        return listMovies.find { it.id == id }
    }

    suspend fun updateMovie(id: UUID, updatedMovie: MovieData): Boolean {
        delay(2000)
        val index = listMovies.indexOfFirst { it.id == id }
        if (index != -1) {
            listMovies[index] = updatedMovie
            return true
        } else {
            return false
        }
    }

    suspend fun deleteMovie(id: UUID): Boolean {
        delay(2000)
        return listMovies.removeIf { it.id == id }
    }
}