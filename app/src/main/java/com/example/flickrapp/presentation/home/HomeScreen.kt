package com.example.flickrapp.presentation.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    updateSearch: (String) -> Unit,
    search: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val searchSuggestions = state.searchSuggestions.collectAsState()
    val searchText = state.searchText.collectAsState()
    val photos = state.searchedPhotos.collectAsState()
    val searchPadding =
        animateDpAsState(
            targetValue = if (expanded.not()) 16.dp else 0.dp,
            animationSpec = tween(200),
        )

    Box(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize()) {
            SearchBar(
                modifier =
                    Modifier
                        .padding(horizontal = searchPadding.value).clickable { expanded = true }
                        .align(Alignment.TopCenter),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchText.value,
                        onQueryChange = { updateSearch(it) },
                        onSearch = {
                            search()
                            expanded = false
                        },
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = it
                        },
                        placeholder = { Text("Search Photos") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier =
                            Modifier.clickable {
                                if (expanded.not()) expanded = true;
                            },
                    )
                },
                expanded = expanded,
                onExpandedChange = {  }
            ) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    searchSuggestions.value.forEach { suggestion ->
                        ListItem(
                            headlineContent = { Text(suggestion) },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = null,
                                )
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier =
                                Modifier
                                    .clickable {
                                        updateSearch(suggestion)
                                        search()
                                        expanded = false
                                        Timber.d("Meep Suggestion")
                                    }.fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                        )
                    }
                }
            }

            LazyColumn(
                contentPadding =
                    PaddingValues(
                        top = SearchBarDefaults.InputFieldHeight,
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(photos.value) {
                    Box(Modifier.fillMaxWidth()) {
                    }
                    AsyncImage(
                        model = it.url,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}
