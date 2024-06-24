package com.example.flickrapp.data

import com.example.flickrapp.constants.FLICKR_API_KEY
import retrofit2.http.GET

interface PhotoApi {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&text=Yorkshire&api_key=$FLICKR_API_KEY")
    suspend fun doNetworkCall() : PhotosSearchResponse
}

// The outermost wrapper for the api response
data class PhotosSearchResponse(
    val photos: PhotosMetaData
)

data class PhotosMetaData(
    val page: Int,
    val photo: List<PhotoResponse>
)

data class PhotoResponse(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String
)