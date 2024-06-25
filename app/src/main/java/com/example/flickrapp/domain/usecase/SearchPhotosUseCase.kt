package com.example.flickrapp.domain.usecase

import com.example.flickrapp.domain.model.Photo
import com.example.flickrapp.domain.repository.PhotoRepository

enum class SearchType{
    TEXT,
    USER
}

class SearchPhotosUseCase(
    private val photoRepository: PhotoRepository,
) {
    suspend fun invoke(search: String, searchType: SearchType): List<Photo> {
        val photosApiResponse = when(searchType){
            SearchType.TEXT -> photoRepository.searchPhotosByText(search)
            SearchType.USER -> photoRepository.searchPhotosByUser(search)
        }
        val photoList =
            photosApiResponse.photos.photo.map { photo ->
                Photo(
                    id = photo.id,
                    url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                    title = photo.title,
                )
            }
        return photoList
    }
}
