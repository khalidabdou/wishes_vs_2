package com.wishes.jetpackcompose.runtime

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
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
    val image: Int,
    val route: String
)


object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Latest",
            image = R.drawable.home,
            route = "home"
        ),

        BarItem(
            title = "Categories",
            image = R.drawable.cats,
            route = "categories"
        ),

        BarItem(
            title = "Favorites",
            image = R.drawable.favs,
            route = "favorites"
        )
    )
}