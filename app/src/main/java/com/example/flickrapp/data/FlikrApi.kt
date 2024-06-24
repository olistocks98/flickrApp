package com.example.flickrapp.data

import com.example.flickrapp.constants.FLICKR_API_KEY
import com.example.flickrapp.constants.FLICKR_FORMAT
import com.example.flickrapp.constants.FLICKR_PHOTOS_METHOD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {
    @GET("rest")
    fun doNetworkCall(
        @Query("method") method: String = FLICKR_PHOTOS_METHOD,
        @Query("format") format : String = FLICKR_FORMAT,
        @Query("nojsoncallback") noJsonCallback : Int = 1,
        @Query("text") text : String,
        @Query("api_key") apiKey : String = FLICKR_API_KEY,
    ): Call<PhotosSearchResponse>
}

data class PhotosSearchResponse(
    val photos: PhotosMetaData = PhotosMetaData(page = -1, photo = listOf()),
)

data class PhotosMetaData(
    val page: Int,
    val photo: List<PhotoResponse>,
)

data class PhotoResponse(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
)

