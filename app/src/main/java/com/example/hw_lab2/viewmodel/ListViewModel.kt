package com.example.hw_lab2.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw_lab2.model.MovieData
import com.example.hw_lab2.model.MovieRepository
import kotlinx.coroutines.launch
import java.util.UUID

class ListViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _movieList = mutableStateListOf<MovieData>()
    val movieList: List<MovieData> get() = _movieList

    private val _movie = mutableStateOf<MovieData?>(null)
    val movie: State<MovieData?> get() = _movie

    init {
        getAllMoviesData()
    }

    private fun getAllMoviesData() {
        viewModelScope.launch {
            val movies = movieRepository.getMovies()
            _movieList.clear()
            _movieList.addAll(movies)
        }
    }

    fun toggleIsWatched(movieId: UUID) {
        val index = _movieList.indexOfFirst { it.id == movieId }
        if (index != -1) {
            val currentMovie = _movieList[index]
            val updatedMovie = currentMovie.copy(isWatched = !currentMovie.isWatched)
            _movieList[index] = updatedMovie

            viewModelScope.launch {
                movieRepository.updateMovie(updatedMovie.id, updatedMovie)
            }
        }
    }

    fun addMovie(newMovie: MovieData) {
        viewModelScope.launch {
            movieRepository.addMovie(newMovie)
            _movieList.add(newMovie)
        }
    }

    fun updateMovie(updatedMovie: MovieData) {
        val index = _movieList.indexOfFirst { it.id == updatedMovie.id }
        if (index != -1) {
            _movieList[index] = updatedMovie
            _movie.value = null
        }

        viewModelScope.launch {
            movieRepository.updateMovie(updatedMovie.id, updatedMovie)
        }
    }

    fun loadMovieById(id: UUID) {
        viewModelScope.launch {
            _movie.value = null
            val movieData = movieRepository.getMovieById(id)
            _movie.value = movieData
        }
    }

    fun deleteMovie(id: UUID) {
        viewModelScope.launch {
            val isDeleted = movieRepository.deleteMovie(id)
            if (isDeleted) {
                _movieList.removeAll { it.id == id }
            }
        }
    }
}