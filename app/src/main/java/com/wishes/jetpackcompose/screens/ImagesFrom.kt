package com.wishes.jetpackcompose.screens

sealed class ImagesFrom(val route:String){
    object Latest:ImagesFrom("latest")
    object ByCat:ImagesFrom("by_cat")
    object Fav:ImagesFrom("fav")
}
