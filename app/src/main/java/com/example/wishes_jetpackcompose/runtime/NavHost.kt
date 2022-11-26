package com.example.wishes_jetpackcompose.runtime

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CornerSize

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wishes_jetpackcompose.*
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
