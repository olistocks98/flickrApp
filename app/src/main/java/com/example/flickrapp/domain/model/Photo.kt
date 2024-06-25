package com.example.flickrapp.domain.model

import com.example.flickrapp.data.O
import com.example.flickrapp.data.Owner
import com.example.flickrapp.data.Tag

data class PhotoInfo(
    val id: String,
    val secret: String,
    val farm: String,
    val dateUploaded: String,
    val owner: Owner,
    val tags: Tag,
)


data class Photo(
    val id: String,
    val url: String,
    val title: String,
    val description: String = "",
    val dateUploaded: String = "",
    val owner: Owner? = null,
    val tags: List<O> = listOf()
)
