package com.example.wishes_jetpackcompose

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.example.wishes_jetpackcompose.data.entities.Page
import com.example.wishes_jetpackcompose.runtime.NavRoutes
import com.example.wishes_jetpackcompose.screens.ImagesFrom
import com.example.wishes_jetpackcompose.utlis.Const.Companion.directoryUpload
import com.example.wishes_jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.example.wishes_jetpackcompose.utlis.loadPicture
import com.example.wishes_jetpackcompose.viewModel.ImagesViewModel
import com.ringtones.compose.feature.admob.showInterstitialAfterClick
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun Home(viewModel: ImagesViewModel, navHostController: NavHostController) {
    val scrollState = rememberLazyGridState()
    val context = LocalContext.current

    val lazyGridState = LazyGridState
    val lifecycleOwner: LifecycleOwner
    LaunchedEffect(Unit) {
        if (viewModel.imageslist.isEmpty()){
            viewModel.getImagesRoom()
            scrollState.scrollToItem(0)
        }

    }

//    val imageLoader = ImageLoader.Builder(context)
//        .diskCache {
//            DiskCache.Builder()
//                .directory(context.cacheDir.resolve("image_cache"))
//                .maxSizePercent(0.02)
//                .build()
//        }
//        .build()

    /*  val firstItemVisible by remember {
          derivedStateOf {f
              scrollState.firstVisibleItemIndex == 18
          }
      }*/

    val images = viewModel.imageslist
    LazyVerticalGrid(
        state = scrollState,
        columns = GridCells.Adaptive(128.dp),
        content = {
            /* Log.d("scroll",scrollState.firstVisibleItemIndex.toString())
             if (firstItemVisible) {
                 //viewModel.offset=40
                 Toast.makeText(context,"last",Toast.LENGTH_LONG).show()
             }*/
            if (images.isEmpty()) {
                items(10) {
                    LoadingShimmerEffectImage()
                }
            } else {
                items(images.size) {
                    val image = loadPicture(
                        url = directoryUpload + images[it].languageLable + "/" + images[it].image_upload,
                        defaultImage = DEFAULT_RECIPE_IMAGE
                    ).value
                    image?.let { img ->
                        ImageItem(
                            img.asImageBitmap(),
                        ) {
                            val page = Page(
                                page = it,
                                imagesList = ImagesFrom.Latest.route,
                                null
                            )
                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "page",
                                value = page
                            )

                            navHostController.navigate(NavRoutes.ViewPager.route)
                        }
                    }
//                    val painter = rememberAsyncImagePainter(
//                        model = directoryUpload + images[it].languageLable + "/" + images[it].image_upload,
//                        imageLoader = imageLoader,
//                        filterQuality= FilterQuality.Low
//
//                    )
//                    ImageItem( painter = painter){
//                        val page=Page(
//                            page = it,
//                            imagesList = ImagesFrom.Latest.route,
//                            null
//                        )
//                        navHostController.currentBackStackEntry?.savedStateHandle?.set(
//                            key="page",
//                            value = page
//                        )
//
//                        navHostController.navigate(NavRoutes.ViewPager.route)
//                    }
                }
            }
        })
}


@Composable
fun ImageItem(painter: ImageBitmap, onClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                onClick()
                showInterstitialAfterClick(context)
            },

        ) {
        Image(
            bitmap = painter, contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun LoadingShimmerEffectImage() {
    //These colors will be used on the brush. The lightest color should be in the middle
    val gradient = listOf(
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f), //darker grey (90% opacity)
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f), //lighter grey (30% opacity)
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition() // animate infinite times

    val translateAnimation = transition.animateFloat( //animate the transition
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = FastOutLinearInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
    ShimmerGridItemImage(brush = brush)
}

@Composable
fun ShimmerGridItemImage(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 6.dp), verticalAlignment = Alignment.Top
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(brush)
        )
    }
}
