package com.example.flickrapp.data.repository

import com.example.flickrapp.data.FlickrApi
import com.example.flickrapp.data.PhotoInfoResponse
import com.example.flickrapp.data.PhotosSearchResponse
import com.example.flickrapp.domain.repository.PhotoRepository
import retrofit2.await

class PhotoRepositoryImpl(
    private val photoApi: FlickrApi,
) : PhotoRepository {
    override suspend fun searchPhotosByText(
        searchText: String,
        tags: String,
        tagMode: String,
    ): PhotosSearchResponse = photoApi.searchPhotosByText(text = searchText, tags = tags, tagMode = tagMode).await()

    override suspend fun searchPhotosByUser(userID: String): PhotosSearchResponse = photoApi.searchPhotosByUser(text = userID).await()

    override suspend fun getPhotoInfo(photoID: String): PhotoInfoResponse = photoApi.getPhotoInfo(photoID = photoID).await()
}
