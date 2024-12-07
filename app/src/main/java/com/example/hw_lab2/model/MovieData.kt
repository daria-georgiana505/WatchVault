package com.example.hw_lab2.model

import com.example.hw_lab2.enums.Genre
import java.time.Year
import java.util.UUID

data class MovieData(
    val id: UUID = UUID.randomUUID(),
    var title: String,
    var genre: Genre,
    var directors: List<String> = listOf(),
    var imageUrl: String? = null,
    var releaseYear: Year,
    var isWatched: Boolean = false
)
