package com.example.wishes_jetpackcompose.runtime

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wishes_jetpackcompose.ByCat
import com.example.wishes_jetpackcompose.Categories
import com.example.wishes_jetpackcompose.Favorites
import com.example.wishes_jetpackcompose.Home
import com.example.wishes_jetpackcompose.data.entities.Page
import com.example.wishes_jetpackcompose.screens.ViewPager
import com.example.wishes_jetpackcompose.viewModel.ImagesViewModel


@Composable
fun NavigationHost(navController: NavHostController) {
    val viewModel: ImagesViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route,
    ) {
        composable(NavRoutes.Home.route) {
            Home(viewModel, navController)
        }

        composable(NavRoutes.Favorites.route) {
            Favorites(viewModel, navController)
        }
        composable(NavRoutes.Categories.route) {
            Categories(viewModel, navController)
        }

        composable(NavRoutes.ByCat.route+"/{id}") {
            val id=it.arguments?.getString("id")
            id.let { id->
                ByCat(viewModel,navController,id!!.toInt())
            }

        }

        composable(NavRoutes.ViewPager.route) {
             navController.previousBackStackEntry?.savedStateHandle?.get<Page>("page").let {
                ViewPager(viewModel, it?.page, it?.imagesList,it?.cat)
            }
        }

    }
}
