package com.sujibfr.app.presentation.home.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapp.data.Owner
import com.example.flickrapp.data.PhotoInfo
import com.example.flickrapp.domain.model.Photo
import com.example.flickrapp.domain.usecase.GetPhotoInfoUseCase
import com.example.flickrapp.domain.usecase.SearchPhotosUseCase
import com.example.flickrapp.domain.usecase.SearchType
import com.example.flickrapp.presentation.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Suppress("ktlint:standard:backing-property-naming")
@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val searchPhotosUseCase: SearchPhotosUseCase,
        private val getPhotoInfoUseCase: GetPhotoInfoUseCase,
    ) : ViewModel() {
        private val _searchText: MutableStateFlow<String> =
            MutableStateFlow("Yorkshire")

        private val _searchedUser: MutableStateFlow<Owner?> =
            MutableStateFlow(null)

        private val _searchSuggestions: MutableStateFlow<List<String>> =
            MutableStateFlow(listOf("Yorkshire", "Leeds", "Nottingham"))

        private val _searchedPhotos: MutableStateFlow<Map<Photo, PhotoInfo?>> =
            MutableStateFlow(mapOf())

        private val _userPhotos: MutableStateFlow<Map<Photo, PhotoInfo?>> =
            MutableStateFlow(mapOf())

        val state: HomeState =
            HomeState(
                searchText = _searchText,
                searchUser = _searchedUser,
                searchSuggestions = _searchSuggestions,
                searchedPhotos = _searchedPhotos,
                userPhotos = _userPhotos,
            )

        init {
            searchPhotosByText()
        }

        private fun searchPhotosByText() {
            viewModelScope.launch {
                _searchedPhotos.value =
                    searchPhotosUseCase
                        .invoke(
                            _searchText.value,
                            searchType = SearchType.TEXT,
                        ).associateWith { null }
                val photos = _searchedPhotos.value.toMutableMap()
                _searchedPhotos.value.keys.forEach { photo ->
                    val photoInfo = getPhotoInfoUseCase.invoke(photo.id)
                    photos.replace(photo, photoInfo.photo)
                }
                _searchedPhotos.value = photos
            }
        }

        private fun searchPhotosByUser() {
            viewModelScope.launch {
                _searchedUser.value?.let {
                    _userPhotos.value =
                        searchPhotosUseCase
                            .invoke(
                                it.nsid,
                                searchType = SearchType.USER,
                            ).associateWith { null }
                    val photos = _userPhotos.value.toMutableMap()
                    _userPhotos.value.keys.forEach { photo ->
                        val photoInfo = getPhotoInfoUseCase.invoke(photo.id)
                        photos.replace(photo, photoInfo.photo)
                    }
                    _userPhotos.value = photos
                }
            }
        }

        fun updateSearchText(newSearchText: String) {
            _searchText.value = newSearchText
        }

        fun updateSearchUser(searchedUser: Owner?) {
            _searchedUser.value = searchedUser
            searchPhotosByUser()
        }

        fun search() {
            searchPhotosByText()
        }
    }
