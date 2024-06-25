package com.example.flickrapp.presentation.home

import com.example.flickrapp.data.Owner
import com.example.flickrapp.domain.model.Photo
import kotlinx.coroutines.flow.StateFlow

data class HomeState(
    val searchText: StateFlow<String>,
    val searchSuggestions: StateFlow<List<String>>,
    val searchedPhotos: StateFlow<List<Photo>>,
    val userPhotos: StateFlow<List<Photo>>,
    val searchUser: StateFlow<Owner?>,
)
