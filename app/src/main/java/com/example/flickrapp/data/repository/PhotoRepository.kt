package com.example.flickrapp.data.repository

import com.example.flickrapp.data.PhotoApi
import com.example.flickrapp.data.PhotosSearchResponse
import com.example.flickrapp.domain.repository.PhotoRepository
import retrofit2.await


class PhotoRepositoryImpl(
    private val photoApi : PhotoApi
) : PhotoRepository {

    override suspend fun doNetworkCall(searchText: String): PhotosSearchResponse {
        return photoApi.doNetworkCall(text = searchText).await()
    }
}