package com.sujibfr.app.presentation.home.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapp.domain.repository.PhotoRepository
import com.example.flickrapp.presentation.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val photoRepository: PhotoRepository,
        @ApplicationContext val context: Context,
    ) : ViewModel() {
        private val _searchText: MutableStateFlow<String> =
            MutableStateFlow("Yorkshire")

        private val _searchSuggestions: MutableStateFlow<List<String>> =
            MutableStateFlow(listOf("Yorkshire", "Leeds", "Nottingham"))

        private val _searchedPhotos: MutableStateFlow<List<Photo>> =
            MutableStateFlow(listOf())

        val state: HomeState =
            HomeState(
                searchText = _searchText,
                searchSuggestions = _searchSuggestions,
                searchedPhotos = _searchedPhotos,
            )

        init {
            searchPhotos()
        }

        fun searchPhotos() {
            viewModelScope.launch {
                val photosApiResponse = photoRepository.doNetworkCall(_searchText.value)
                val photoList =
                    photosApiResponse.photos.photo.map { photo ->
                        Photo(
                            id = photo.id,
                            url = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg",
                            title = photo.title,
                        )
                    }
                _searchedPhotos.value = photoList
            }
        }

        fun updateSearchText(newSearchText: String) {
            _searchText.value = newSearchText
        }
    }

data class Photo(
    val id: String,
    val url: String,
    val title: String,
)
