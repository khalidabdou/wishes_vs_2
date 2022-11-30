package com.example.wishes_jetpackcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.example.wishes_jetpackcompose.runtime.NavRoutes
import com.example.wishes_jetpackcompose.utlis.Const
import com.example.wishes_jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.example.wishes_jetpackcompose.utlis.loadPicture
import com.example.wishes_jetpackcompose.viewModel.ImagesViewModel
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

    val images =viewModel.favoritesList
    LazyVerticalGrid(
        state = scrollState,
        columns = GridCells.Adaptive(128.dp),
        // content padding
        content = {
            if (images.isEmpty()){
                items(10) {
                    repeat(10){
                        LoadingShimmerEffectImage()
                    }
                }
            }else{
                items(images.size) {
//                    val painter = rememberAsyncImagePainter(
//                        model = Const.directoryUpload + images[it].languageLable + "/" + images[it].image_upload,
//                        imageLoader = imageLoader,
//                        filterQuality= FilterQuality.Low
//
//                    )
                    val image = loadPicture(url =  Const.directoryUpload + images[it].languageLable + "/" + images[it].image_upload,
                        defaultImage = DEFAULT_RECIPE_IMAGE
                    ).value
                    image?.let { img ->
                        ImageItem(
                            img.asImageBitmap(),
                        ){
                            navHostController.navigate(NavRoutes.ViewPager.route+"/"+it)
                        }
                    }
                   /* ImageItem( painter = painter){
                        navHostController.navigate(NavRoutes.ViewPager.route+"/"+it)
                    }*/
                }
            }
        })
}