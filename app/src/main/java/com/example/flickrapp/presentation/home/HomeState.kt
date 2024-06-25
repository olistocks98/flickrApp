package com.example.flickrapp.presentation.home

import com.example.flickrapp.data.Owner
import com.example.flickrapp.data.PhotoInfo
import com.example.flickrapp.domain.model.Photo
import kotlinx.coroutines.flow.StateFlow

data class HomeState(
    val searchText: StateFlow<String>,
    val searchSuggestions: StateFlow<List<String>>,
    val searchedPhotos: StateFlow<Map<Photo, PhotoInfo?>>,
    val userPhotos: StateFlow<Map<Photo, PhotoInfo?>>,
    val searchUser: StateFlow<Owner?>,
)
