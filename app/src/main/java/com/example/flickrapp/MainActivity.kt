package com.example.flickrapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flickrapp.presentation.home.HomeScreen
import com.example.flickrapp.ui.theme.FlickrAppTheme
import com.sujibfr.app.presentation.home.viewModels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModelHome = hiltViewModel<SearchViewModel>()
            FlickrAppTheme {
                val navController = rememberNavController()
                Navigation(navController, viewModelHome)
            }
        }
    }

    @Composable
    private fun Navigation(
        navController: NavHostController,
        viewModel: SearchViewModel,
    ) {
        NavHost(
            navController = navController,
            startDestination = "screenHome",
        ) {
            composable("screenHome", content = {
                HomeScreen(
                    viewModel.state,
                    updateSearch = { text -> viewModel.updateSearchText(text) },
                    updateSearchByUser = { nsid -> viewModel.updateSearchUser(nsid) },
                    search = { viewModel.search() },
                    setTagSearchMode = { tagSearchMode -> viewModel.setTagSearchMode(tagSearchMode) },
                )
            })
        }
    }
}
