package com.example.flickrapp.data.responseData

data class PhotoInfo(
    val id: String,
    val secret: String,
    val farm: String,
    val dateUploaded: String,
    val owner: Owner,
    val description: Description,
    val tags : TagResponse
)