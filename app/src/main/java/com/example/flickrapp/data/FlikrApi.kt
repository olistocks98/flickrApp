package com.example.flickrapp.data

import com.example.flickrapp.constants.FLICKR_API_KEY
import com.example.flickrapp.constants.FLICKR_FORMAT
import com.example.flickrapp.constants.FLICKR_GET_PHOTO_INFO
import com.example.flickrapp.constants.FLICKR_SEARCH_PHOTOS_METHOD
import com.example.flickrapp.data.responseData.PhotoInfoResponse
import com.example.flickrapp.data.responseData.PhotosSearchResponse
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
        @Query("per_page") perPage: Int = 10,
        @Query("safe_search") safeSearch: Int = 1,
        @Query("tags") tags: String,
        @Query("tag_mode") tagMode: String = "any",
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


