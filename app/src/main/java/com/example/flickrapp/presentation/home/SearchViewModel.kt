package com.sujibfr.app.presentation.home.viewModels

import TagSearchMode
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrapp.data.responseData.Owner
import com.example.flickrapp.domain.model.Photo
import com.example.flickrapp.domain.usecase.SearchPhotosUseCase
import com.example.flickrapp.helpers.removeTags
import com.example.flickrapp.helpers.toTagList
import com.example.flickrapp.presentation.home.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("ktlint:standard:backing-property-naming")
@HiltViewModel
class SearchViewModel
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

        private val _tagSearchMode: MutableStateFlow<TagSearchMode> =
            MutableStateFlow(TagSearchMode.SOME_TAGS)

        private var searchJob: Job? = null

        val state: SearchState =
            SearchState(
                searchText = _searchText,
                searchUser = _searchUser,
                searchSuggestions = _searchSuggestions,
                searchedPhotos = _searchPhotos,
                userPhotos = _userPhotos,
                tagSearchMode = _tagSearchMode,
            )

        init {
            searchPhotosByText()
        }

        fun setTagSearchMode(tagSearchMode: TagSearchMode) {
            _tagSearchMode.value = tagSearchMode
        }

        private fun searchPhotosByText() {
            val tags = _searchText.value.toTagList()
            val searchText = _searchText.value.removeTags()
            searchJob?.cancel()
            searchJob =
                searchPhotosUseCase
                    .invoke(
                        searchType = SearchType.TEXT,
                        search = searchText,
                        tags = tags,
                        tagMode = _tagSearchMode.value,
                    ).onEach {
                        _searchPhotos.value = it
                    }.launchIn(viewModelScope)
        }

        private fun searchPhotosByUser() {
            _searchUser.value?.nsid?.let { nsid ->
                searchPhotosUseCase
                    .invoke(
                        search = nsid,
                        searchType = SearchType.USER,
                        tagMode = _tagSearchMode.value,
                    ).onEach { result ->
                        _userPhotos.value = result
                    }.launchIn(viewModelScope)
            }
        }

        fun updateSearchText(newSearchText: String) {
            _searchText.value = newSearchText
        }

        fun appendTag(tag: String) {
            _searchText.value += " #$tag"
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
