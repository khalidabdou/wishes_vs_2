package com.example.wishes_jetpackcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.wishes.jetpackcompose.data.entities.Page
import com.wishes.jetpackcompose.runtime.NavRoutes
import com.wishes.jetpackcompose.screens.ImagesFrom
import com.wishes.jetpackcompose.utlis.Const
import com.wishes.jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.wishes.jetpackcompose.utlis.loadPicture
import com.wishes.jetpackcompose.viewModel.ImagesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun Favorites(viewModel: ImagesViewModel, navHostController: NavHostController) {

    val context = LocalContext.current
    val scrollState = rememberLazyGridState()
    val lazyGridState = LazyGridState
    val lifecycleOwner: LifecycleOwner
    LaunchedEffect(Unit) {
        viewModel.getFavoritesRoom()
    }

//    val imageLoader = ImageLoader.Builder(context)
//        .diskCache {
//            DiskCache.Builder()
//                .directory(context.cacheDir.resolve("image_cache"))
//                .maxSizePercent(0.02)
//                .build()
//        }
//        .build()

    val images = viewModel.favoritesList
    if (images.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(70.dp)
            )
            Text(
                text = "Empty",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    } else
        LazyVerticalGrid(
            state = scrollState,
            columns = GridCells.Adaptive(128.dp),
            modifier = Modifier.fillMaxSize(),
            content = {


                items(images.size) {
//                    val painter = rememberAsyncImagePainter(
//                        model = Const.directoryUpload + images[it].languageLable + "/" + images[it].image_upload,
//                        imageLoader = imageLoader,
//                        filterQuality= FilterQuality.Low
//
//                    )
                    val image = loadPicture(
                        url = Const.directoryUpload + images[it].languageLable + "/" + images[it].image_upload,
                        defaultImage = DEFAULT_RECIPE_IMAGE
                    ).value
                    image?.let { img ->
                        ImageItem(
                            img.asImageBitmap(),
                        ) {
                            val page = Page(
                                page = it,
                                imagesList = ImagesFrom.Fav.route,
                                null
                            )
                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "page",
                                value = page
                            )
                            navHostController.navigate(NavRoutes.ViewPager.route)
                        }
                    }
                    /* ImageItem( painter = painter){
                         navHostController.navigate(NavRoutes.ViewPager.route+"/"+it)
                     }*/
                }

            })
}