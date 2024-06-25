package com.example.flickrapp.data.responseData

data class PhotosSearchResponse(
    val photos: PhotosMetaData = PhotosMetaData(page = -1, photo = listOf()),
)