package com.wishes.jetpackcompose.runtime

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.wishes.jetpackcompose.R

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Favorites : NavRoutes("favorites")
    object Categories : NavRoutes("categories")
    object ViewPager : NavRoutes("viewPager")
    object ByCat : NavRoutes("byCat")
    object Splash : NavRoutes("splash")
}

data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: String
)


object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Latest",
            image = Icons.Filled.Home,
            route = "home"
        ),

        BarItem(
            title = "Categories",
            image = Icons.Outlined.Menu,
            route = "categories"
        ),

        BarItem(
            title = "Favorites",
            image = Icons.Outlined.Favorite,
            route = "favorites"
        )
    )
}