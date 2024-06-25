package com.example.flickrapp.domain.repository

import com.example.flickrapp.data.responseData.PhotoInfoResponse
import com.example.flickrapp.data.responseData.PhotosSearchResponse

interface PhotoRepository {
    suspend fun searchPhotosByText(searchText: String, tags: String, tagMode: String): PhotosSearchResponse
    suspend fun searchPhotosByUser(userID: String): PhotosSearchResponse
    suspend fun getPhotoInfo(photoID: String): PhotoInfoResponse
}
