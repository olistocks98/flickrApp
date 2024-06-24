package com.example.flickrapp.presentation.home

import com.sujibfr.app.presentation.home.viewModels.Photo
import kotlinx.coroutines.flow.StateFlow

data class HomeState(
    val searchText : StateFlow<String>,
    val searchSuggestions : StateFlow<List<String>>,
    val searchedPhotos : StateFlow<List<Photo>>
)
