package com.example.flickrapp.domain.repository

import com.example.flickrapp.data.PhotosSearchResponse

interface PhotoRepository {
    suspend fun doNetworkCall(searchText : String) : PhotosSearchResponse
}