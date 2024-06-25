package com.example.flickrapp.presentation.home

import TagSearchMode
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.flickrapp.R
import com.example.flickrapp.data.Owner
import com.example.flickrapp.domain.model.Photo

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeScreen(
    state: HomeState,
    updateSearch: (String) -> Unit,
    updateSearchByUser: (Owner?) -> Unit,
    search: () -> Unit,
    appendTag: (String) -> Unit,
    setTagSearchMode: (TagSearchMode) -> Unit,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val searchSuggestions = state.searchSuggestions.collectAsState()
    val searchUser = state.searchUser.collectAsState()
    val tagSearchMode = state.tagSearchMode.collectAsState()
    val searchText = state.searchText.collectAsState()
    val photos = state.searchedPhotos.collectAsState()
    val userPhotos = state.userPhotos.collectAsState()

    val navigator = rememberListDetailPaneScaffoldNavigator<Photo>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    val displayedPhotos by remember {
        derivedStateOf {
            if (searchUser.value == null) {
                photos.value
            } else {
                userPhotos.value
            }
        }
    }

    val searchPadding =
        animateDpAsState(
            targetValue = if (expanded.not()) 16.dp else 0.dp,
            animationSpec = tween(200),
        )

    Scaffold(
        topBar = {
            if (searchUser.value != null || navigator.canNavigateBack()) {
                BackHandler {
                    updateSearchByUser(null)
                }
                TopAppBar(
                    title = { Text(searchUser.value?.username ?: "") },
                    navigationIcon = {
                        IconButton(onClick = {
                            updateSearchByUser(null)
                            navigator.navigateBack()
                        }) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, "back")
                        }
                    },
                )
            }
        },
        content = { padding ->
            Box(Modifier.fillMaxSize()) {
                SearchBar(
                    modifier =
                        Modifier
                            .padding(horizontal = searchPadding.value)
                            .clickable { expanded = true }
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
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = {
                                    dropdownExpanded = dropdownExpanded.not()
                                }) {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = null,
                                    )
                                }
                                DropdownMenu(expanded = dropdownExpanded, onDismissRequest = {
                                    dropdownExpanded = false
                                }) {
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(32.dp),
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                Text("Contains all tags")
                                                if (tagSearchMode.value == TagSearchMode.ALL_TAGS) {
                                                    Icon(Icons.Default.Check, "check")
                                                }
                                            }
                                        },
                                        onClick = {
                                            setTagSearchMode(TagSearchMode.ALL_TAGS)
                                            dropdownExpanded = false
                                            search()
                                        },
                                    )
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(32.dp),
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                Text("Contains some tags")
                                                if (tagSearchMode.value == TagSearchMode.SOME_TAGS) {
                                                    Icon(Icons.Default.Check, "check")
                                                }
                                            }
                                        },
                                        onClick = {
                                            setTagSearchMode(TagSearchMode.SOME_TAGS)
                                            dropdownExpanded = false
                                            search()
                                        },
                                    )
                                }
                            },
                            modifier =
                                Modifier.clickable {
                                    if (expanded.not()) expanded = true
                                },
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { },
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
                                        }.fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 4.dp),
                            )
                        }
                    }
                }

                navigator.canNavigateBack()
                ListDetailPaneScaffold(
                    directive = navigator.scaffoldDirective,
                    value = navigator.scaffoldValue,
                    listPane = {
                        AnimatedPane {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(0.dp),
                            ) {
                                items(displayedPhotos) {
                                    Box(Modifier.fillMaxWidth()) {
                                        AsyncImage(
                                            model = it.url,
                                            contentDescription = null,
                                            modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        navigator.navigateTo(
                                                            ListDetailPaneScaffoldRole.Detail,
                                                            it,
                                                        )
                                                    },
                                            contentScale = ContentScale.Crop,
                                        )
                                        Box(
                                            Modifier
                                                .fillMaxWidth()
                                                .height(65.dp)
                                                .background(Color.Black.copy(alpha = 0.6F))
                                                .align(Alignment.BottomCenter),
                                        ) {
                                            Row(
                                                modifier =
                                                    Modifier
                                                        .align(Alignment.Center)
                                                        .fillMaxWidth()
                                                        .padding(4.dp),
                                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                AsyncImage(
                                                    model = it.owner?.url,
                                                    contentDescription = it.owner?.realname,
                                                    modifier =
                                                        Modifier
                                                            .clickable {
                                                                updateSearchByUser(
                                                                    it.owner,
                                                                )
                                                            }.size(40.dp)
                                                            .clip(CircleShape),
                                                    contentScale = ContentScale.FillBounds,
                                                    placeholder = painterResource(R.drawable.ic_avatar),
                                                )
                                                Column {
                                                    Text(
                                                        text =
                                                            it.owner
                                                                ?.username
                                                                .orEmpty(),
                                                        color = Color.White,
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Bold,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    detailPane = {
                        var showInformation by remember {
                            mutableStateOf(false)
                        }
                        navigator.currentDestination?.content.let { details ->
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                                    .clickable { showInformation = showInformation.not() },
                            ) {
                                AsyncImage(
                                    modifier =
                                        Modifier
                                            .align(Alignment.BottomCenter)
                                            .fillMaxSize(),
                                    model = details?.url.orEmpty(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit,
                                )

                                if (details != null) {
                                    AnimatedVisibility(
                                        showInformation,
                                        enter = fadeIn(),
                                        exit = fadeOut(),
                                    ) {
                                        Box(
                                            Modifier
                                                .fillMaxSize()
                                                .background(Color.Black.copy(alpha = 0.5F)),
                                        ) {
                                            Card(
                                                modifier =
                                                    Modifier
                                                        .widthIn(max = 600.dp)
                                                        .fillMaxWidth()
                                                        .align(Alignment.Center)
                                                        .padding(16.dp),
                                            ) {
                                                Column(
                                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                                ) {
                                                    Column(Modifier.padding(16.dp)) {
                                                        Text(
                                                            details.title,
                                                            style = MaterialTheme.typography.titleMedium,
                                                        )
                                                        Text(
                                                            details.description,
                                                            style = MaterialTheme.typography.bodySmall,
                                                        )
                                                    }
                                                    LazyRow(
                                                        horizontalArrangement =
                                                            Arrangement.spacedBy(
                                                                10.dp,
                                                            ),
                                                    ) {
                                                        item { Spacer(Modifier.width(4.dp)) }
                                                        details.tags.forEach {
                                                            item {
                                                                SuggestionChip(
                                                                    label = { Text(it._content) },
                                                                    onClick = {
                                                                        updateSearch("#${it._content}")
                                                                        search()
                                                                        navigator.navigateBack()
                                                                    },
                                                                )
                                                            }
                                                        }
                                                        item { Spacer(Modifier.width(4.dp)) }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                )
            }
        },
    )
}
