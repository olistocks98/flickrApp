package com.sujibfr.app.presentation.home.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapp.data.Owner
import com.example.flickrapp.domain.model.Photo
import com.example.flickrapp.domain.usecase.SearchPhotosUseCase
import com.example.flickrapp.domain.usecase.SearchType
import com.example.flickrapp.presentation.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("ktlint:standard:backing-property-naming")
@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val searchPhotosUseCase: SearchPhotosUseCase,
    ) : ViewModel() {
        private val _searchText: MutableStateFlow<String> =
            MutableStateFlow("Yorkshire")

        private val _searchUser: MutableStateFlow<Owner?> =
            MutableStateFlow(null)

        private val _searchSuggestions: MutableStateFlow<List<String>> =
            MutableStateFlow(listOf("Yorkshire", "Leeds", "Nottingham"))

        private val _searchPhotos: MutableStateFlow<List<Photo>> =
            MutableStateFlow(listOf())

        private val _userPhotos: MutableStateFlow<List<Photo>> =
            MutableStateFlow(listOf())

        val state: HomeState =
            HomeState(
                searchText = _searchText,
                searchUser = _searchUser,
                searchSuggestions = _searchSuggestions,
                searchedPhotos = _searchPhotos,
                userPhotos = _userPhotos,
            )

        init {
            searchPhotosByText()
        }

        private fun searchPhotosByText() {
            searchPhotosUseCase
                .invoke(searchType = SearchType.TEXT, search = _searchText.value)
                .onEach {
                    _searchPhotos.value = it
                }.launchIn(viewModelScope)
        }

        private fun searchPhotosByUser() {
            _searchUser.value?.nsid?.let { nsid ->
                searchPhotosUseCase
                    .invoke(searchType = SearchType.USER, search = nsid)
                    .onEach { result ->
                        _userPhotos.value = result
                    }.launchIn(viewModelScope)
            }
        }

        fun updateSearchText(newSearchText: String) {
            _searchText.value = newSearchText
        }

        fun updateSearchUser(searchedUser: Owner?) {
            _userPhotos.value = listOf()
            _searchUser.value = searchedUser
            searchPhotosByUser()
        }

        fun search() {
            searchPhotosByText()
        }
    }
