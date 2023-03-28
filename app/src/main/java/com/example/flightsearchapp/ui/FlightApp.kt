package com.example.flightsearchapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun FlightApp(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory)
) {

    val homeUiState = viewModel.homeUiState.collectAsState()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home"){

        composable("home"){
            HomeScreen(
                viewModel = viewModel
            ) { navController.navigate("details") }
        }
        composable("details"){
            DetailsScreen(
                homeUiState = homeUiState,
                viewModel = viewModel

                )
        }

    }

}