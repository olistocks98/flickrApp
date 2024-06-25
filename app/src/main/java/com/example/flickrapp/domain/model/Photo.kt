package com.example.flickrapp.domain.model

import android.os.Parcelable
import com.example.flickrapp.data.Owner
import com.example.flickrapp.data.Tags
import kotlinx.parcelize.Parcelize

data class PhotoInfo(
    val id: String,
    val secret: String,
    val farm: String,
    val dateUploaded: String,
    val owner: Owner,
    val tags: Tags,
)

@Parcelize
data class Photo(
    val id: String,
    val url: String,
    val title: String,
    val dateUploaded: String = "",
    val owner: Owner? = null
) : Parcelable
