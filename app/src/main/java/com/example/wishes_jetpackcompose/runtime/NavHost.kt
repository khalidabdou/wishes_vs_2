package com.example.wishes_jetpackcompose.runtime

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wishes_jetpackcompose.Categories
import com.example.wishes_jetpackcompose.Favorites
import com.example.wishes_jetpackcompose.Home
import com.example.wishes_jetpackcompose.screens.ViewPager
import com.example.wishes_jetpackcompose.viewModel.ImagesViewModel


@Composable
fun NavigationHost(viewModel: ImagesViewModel,navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route,
    ) {
        composable(NavRoutes.Home.route) {
            Home(viewModel,navController)
        }

        composable(NavRoutes.Favorites.route) {
            Favorites()
        }
        composable(NavRoutes.Categories.route) {
            Categories(viewModel)
        }

        composable(NavRoutes.ViewPager.route) {
            ViewPager()
        }

    }
}
