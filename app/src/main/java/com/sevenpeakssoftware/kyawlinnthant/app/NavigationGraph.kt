package com.sevenpeakssoftware.kyawlinnthant.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sevenpeakssoftware.kyawlinnthant.presentation.detail.DetailScreen
import com.sevenpeakssoftware.kyawlinnthant.presentation.home.HomeScreen

@Composable
fun CarsGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Home
    ) {
        composable(
            route = Destinations.Home
        ) {
            HomeScreen(
                navController = navController
            )
        }
        composable(
            route = Destinations.Detail + "/{id}",
            arguments = listOf(
                navArgument(name = "id") { type = NavType.IntType }
            )
        ) {
            val id = it.arguments?.getString("id")
            it.savedStateHandle["id"] = id
            DetailScreen()
        }
    }
}
