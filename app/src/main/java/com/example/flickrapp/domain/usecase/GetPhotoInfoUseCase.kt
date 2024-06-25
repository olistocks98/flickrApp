package com.example.flickrapp.domain.usecase

import com.example.flickrapp.data.PhotoInfoResponse
import com.example.flickrapp.domain.repository.PhotoRepository

class GetPhotoInfoUseCase(
    private val photoRepository: PhotoRepository,
) {
    suspend fun invoke(photoID: String): PhotoInfoResponse = photoRepository.getPhotoInfo(photoID)
}
