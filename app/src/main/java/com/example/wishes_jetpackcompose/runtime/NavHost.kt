package com.example.wishes_jetpackcompose.runtime

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wishes_jetpackcompose.Categories
import com.example.wishes_jetpackcompose.Favorites
import com.example.wishes_jetpackcompose.Home
import com.example.wishes_jetpackcompose.screens.ViewPager


@Composable
fun NavigationHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route,
    ) {
        composable(NavRoutes.Home.route) {
            Home(navController)
        }

        composable(NavRoutes.Favorites.route) {
            Favorites()
        }
        composable(NavRoutes.Categories.route) {
            Categories()
        }

        composable(NavRoutes.ViewPager.route) {
            ViewPager()
        }

    }
}
