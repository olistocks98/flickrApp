package com.example.flickrapp.data

import com.example.flickrapp.constants.FLICKR_API_KEY
import com.example.flickrapp.constants.FLICKR_FORMAT
import com.example.flickrapp.constants.FLICKR_GET_PHOTO_INFO
import com.example.flickrapp.constants.FLICKR_SEARCH_PHOTOS_METHOD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("rest")
    fun searchPhotosByText(
        @Query("method") method: String = FLICKR_SEARCH_PHOTOS_METHOD,
        @Query("format") format: String = FLICKR_FORMAT,
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("text") text: String,
        @Query("api_key") apiKey: String = FLICKR_API_KEY,
        @Query("per_page") perPage: Int = 10
    ): Call<PhotosSearchResponse>
    @GET("rest")
    fun searchPhotosByUser(
        @Query("user_id") text: String,
        @Query("method") method: String = FLICKR_SEARCH_PHOTOS_METHOD,
        @Query("format") format: String = FLICKR_FORMAT,
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("api_key") apiKey: String = FLICKR_API_KEY,
        @Query("per_page") perPage: Int = 10
    ): Call<PhotosSearchResponse>
    @GET("rest")
    fun getPhotoInfo(
        @Query("method") method: String = FLICKR_GET_PHOTO_INFO,
        @Query("format") format: String = FLICKR_FORMAT,
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("photo_id") photoID: String,
        @Query("api_key") apiKey: String = FLICKR_API_KEY,
    ): Call<PhotoInfoResponse>
}

data class PhotosSearchResponse(
    val photos: PhotosMetaData = PhotosMetaData(page = -1, photo = listOf()),
)

data class PhotoInfoResponse(
    val photo: PhotoInfo,
)

data class PhotoInfo(
    val id: String,
    val secret: String,
    val farm: String,
    val dateUploaded: String,
    val owner: Owner,
    val tags: Tags,
)

data class Owner(
    val nsid: String,
    val username: String,
    val realname: String,
    val location: String,
    val iconserver: String,
    val iconfarm: String,
){
    val url : String
        get() =  "https://farm" + "${iconfarm}.staticflickr.com/${iconserver}/buddyicons/${nsid}.jpg"
}

data class Tags(
    val id: String,
    val author: String,
    val authorName: String,
    val _content: String,
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
