package com.example.flickrapp.domain.usecase

import com.example.flickrapp.domain.model.Photo
import com.example.flickrapp.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

enum class SearchType {
    TEXT,
    USER,
}

class SearchPhotosUseCase(
    private val photoRepository: PhotoRepository,
) {
    fun invoke(
        search: String,
        searchType: SearchType,
    ): Flow<List<Photo>> =
        flow {
            val photosApiResponse =
                when (searchType) {
                    SearchType.TEXT -> photoRepository.searchPhotosByText(search)
                    SearchType.USER -> photoRepository.searchPhotosByUser(search)
                }
            var photoList =
                photosApiResponse.photos.photo
                    .map { photo ->
                        Photo(
                            id = photo.id,
                            url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                            title = photo.title,
                        )
                    }.toMutableList()
            emit(photoList)
            photoList.forEachIndexed { index, photo ->
                val photoInfo = photoRepository.getPhotoInfo(photo.id)
                photoList =
                    photoList
                        .mapIndexed { updatingIndex, updatingPhoto ->
                            if (index == updatingIndex) {
                                updatingPhoto.copy(
                                    owner = photoInfo.photo.owner,
                                    description = photoInfo.photo.description._content,
                                    tags = photoInfo.photo.tags.tag
                                )
                            } else {
                                updatingPhoto
                            }
                        }.toMutableList()
                emit(photoList)
            }
        }
}
