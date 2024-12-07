# WatchVault

A Jetpack Compose application for managing a personal movie collection. The app allows users to add, view, update, and delete movies while tracking whether they have been watched. It incorporates an intuitive and modern UI built with Material Design 3.

## Features

- **Movie List:** Displays a list of movies with details like title, genre, directors, release year, and a thumbnail image.
- **Add/Update Movie:** Provides a form to add new movies or edit existing ones, complete with validation.
- **Movie Details:** View detailed information about a movie and update its attributes.
- **Watched Status Toggle:** Allows marking movies as "watched" or "not watched."
- **Swipe to Delete:** Swipe left or right on a movie card to delete it from the list.
- **Filters and Inputs:**
  - Genre dropdown menu.
  - Year picker for selecting the movie's release year.
  - Support for multiple directors with a chip-style interface.

## Screenshots

<div style="display: flex; justify-content: center; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/f678120b-dae3-423c-ad29-7a9adf887e91" alt="Home screen" style="width: 30%; height: auto; border: 1px solid #ccc; border-radius: 8px;">
  <img src="https://github.com/user-attachments/assets/b2e6f025-bece-43e7-8013-9423d220842a" alt="Add screen" style="width: 30%; height: auto; border: 1px solid #ccc; border-radius: 8px;">
  <img src="https://github.com/user-attachments/assets/8dfeaa4b-92a0-4ee3-8f1a-fe29437f8bb0" alt="Movie details and update screen" style="width: 30%; height: auto; border: 1px solid #ccc; border-radius: 8px;">
</div>

## Architecture

The app follows the **MVVM (Model-View-ViewModel)** architecture pattern:

- **Model:** Represents movie data (`MovieData`) and repository operations (`MovieRepository`).
- **ViewModel:** Manages the UI state and communicates with the repository to handle data operations.
- **View (Compose):** Contains the composables that render the UI and react to state changes from the ViewModel.

## Dependencies

- **Jetpack Compose:** For building declarative UI.
- **Material3:** Latest Material Design components.
- **Kotlin Coroutines & State Management:** For asynchronous data handling and state updates.

## Prerequisites

- **Android Studio Bumblebee (or later):** Required to build and run the app.
- **Kotlin 1.7+**
- **Gradle 7.2+**

## Installation

1. Clone the repository:

```bash
git clone https://github.com/daria-georgiana505/WatchVault.git
```

2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Run the app on an emulator or connected Android device.

## App Structure

### Data Layer

- **`MovieData` Class:** Represents a movie entity.
- `MovieRepository`: Interface for managing CRUD operations on movie data.

### Presentation Layer

- `ListViewModel`: Provides data for UI composables and handles business logic.
- **Compose Screens:**
  - `HomeScreen`: Displays the movie list with options to add or delete movies.
  - `AddMovieScreen`: A form for adding a new movie.
  - `MovieDetailsScreen`: Allows editing a movie’s details.

### Composables

- `MovieCard`: Displays movie details in a card with a watched status checkbox.
- `MovieForm`: A reusable form component for adding/updating movies.
- `SwipeBox`: Enables swipe-to-delete functionality for list items.
- `DirectorsChips`: Displays and manages a list of directors using chips.

### User Interface

#### Home Screen

- A list of movies categorized by genre.
- A floating action button to add new movies.

#### Add/Update Movie Form

- Input fields for:
  - Title (required)
  - Genre (dropdown menu)
  - Release year (year picker)
  - Directors (add/remove multiple)
  - Optional image URL

#### Movie Details

- Editable view showing all movie information.

#### Delete Swipe

- Swiping left or right on a movie card removes it from the list.
