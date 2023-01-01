package com.example.wishes_jetpackcompose

import android.graphics.Bitmap
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.wishes.jetpackcompose.data.entities.Page
import com.wishes.jetpackcompose.runtime.NavRoutes
import com.wishes.jetpackcompose.screens.ImagesFrom
import com.wishes.jetpackcompose.utlis.Const
import com.wishes.jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.wishes.jetpackcompose.utlis.loadPicture
import com.wishes.jetpackcompose.utlis.loadPicturetemmp
import com.wishes.jetpackcompose.viewModel.ImagesViewModel

import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ByCat(viewModel: ImagesViewModel, navHostController: NavHostController, catId:Int) {

    val context = LocalContext.current
    val scrollState = rememberLazyGridState()

    LaunchedEffect(key1 = true){
        viewModel.getByCatRoom(catId)
    }

    LazyVerticalGrid(
        state = scrollState,
        columns = GridCells.Adaptive(128.dp),

        content = {
            if (viewModel.imagesByCategory.isEmpty()){
                items(10) {
                    repeat(10){
                        LoadingShimmerEffectImage()
                    }
                }
            }else{
                items(viewModel.imagesByCategory.size) {
                    val image: MutableState<Bitmap?>? = loadPicturetemmp(
                        url =  Const.directoryUpload + viewModel.imagesByCategory[it].languageLable + "/" + viewModel.imagesByCategory[it].image_upload,
                        defaultImage = DEFAULT_RECIPE_IMAGE
                    )
                    ImageItem(
                        image?.value?.asImageBitmap(),
                    ) {
                        val page= Page(
                            page = it,
                            imagesList = ImagesFrom.ByCat.route,
                            cat =viewModel.imagesByCategory[it].cat_id
                        )
                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
                            key="page",
                            value = page
                        )
                        navHostController.navigate(NavRoutes.ViewPager.route)
                    }

                }
            }
        })
}