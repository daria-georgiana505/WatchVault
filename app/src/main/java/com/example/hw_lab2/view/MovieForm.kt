package com.example.hw_lab2.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hw_lab2.enums.Genre
import java.time.LocalDate
import java.time.Year


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieForm(
    title: String,
    onTitleChange: (String) -> Unit,
    genre: Genre,
    onGenreChange: (Genre) -> Unit,
    directors: List<String>,
    onDirectorsChange: (List<String>) -> Unit,
    imageUrl: String,
    onImageUrlChange: (String) -> Unit,
    releaseYear: Year,
    onReleaseYearChange: (Year) -> Unit,
    onMovieSaved: () -> Unit
) {

    var newDirector by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    var showToast by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, "Movie title cannot be empty...", Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie") }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = onTitleChange,
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                GenreDropdownMenu(
                    selectedDropdownItem = genre,
                    onGenreSelected = onGenreChange
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = newDirector,
                    onValueChange = { newDirector = it },
                    label = { Text("Director") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (newDirector.isNotBlank()) {
                            val updatedDirectors = directors + newDirector.trim()
                            onDirectorsChange(updatedDirectors)
                            newDirector = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Add Director")
                }

                Spacer(modifier = Modifier.height(8.dp))

                DirectorsChips(
                    directors = directors,
                    onDirectorRemoved = { director ->
                        val updatedDirectors = directors.filter { it != director }
                        onDirectorsChange(updatedDirectors)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = imageUrl,
                    onValueChange = onImageUrlChange,
                    label = { Text("Image URL (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                YearPicker(
                    selectedYear = releaseYear,
                    onYearSelected = onReleaseYearChange
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    if (title.isBlank())
                    {
                        showToast = true
                    }
                    else{
                        onMovieSaved()
                    }
                } ) {
                    Text("Save Movie")
                }
            }
        }
    )
}


@Composable
fun DirectorsChips(directors: List<String>, onDirectorRemoved: (String) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(directors) { director ->
            val (isSelected, setIsSelected) = rememberSaveable { mutableStateOf(false) }

            InputChip(
                selected = isSelected,
                onClick = { setIsSelected(!isSelected) },
                modifier = Modifier.padding(end = 8.dp),
                label = { Text(director) },
                trailingIcon = {
                    IconButton(onClick = { onDirectorRemoved(director) }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Remove Director")
                    }
                }
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreDropdownMenu( selectedDropdownItem: Genre, onGenreSelected: (Genre) -> Unit, modifier: Modifier = Modifier )
{
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedDropdownItem.name,
            onValueChange = {},
            label = { Text("Genre") },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            Genre.entries.forEach { genre ->
                DropdownMenuItem(
                    text = {
                        Text(text = genre.name)
                    },
                    onClick = {
                        onGenreSelected(genre)
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearPicker(
    selectedYear: Year,
    onYearSelected: (Year) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentYear = LocalDate.now().year
    val years = (currentYear downTo 1900).toList()

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedYear.value.toString(),
            onValueChange = {},
            label = { Text("Select Year") },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            years.forEach { year ->
                DropdownMenuItem(
                    text = { Text(text = year.toString()) },
                    onClick = {
                        onYearSelected(Year.of(year))
                        isExpanded = false
                    }
                )
            }
        }
    }
}
