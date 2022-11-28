package com.example.wishes_jetpackcompose

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import com.example.wishes_jetpackcompose.data.entities.Images
import com.example.wishes_jetpackcompose.runtime.NavRoutes
import com.example.wishes_jetpackcompose.utlis.Const
import com.example.wishes_jetpackcompose.utlis.Const.Companion.directoryUpload
import com.example.wishes_jetpackcompose.utlis.NetworkResults
import com.example.wishes_jetpackcompose.viewModel.ImagesViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.FilterQuality
import coil.request.ImageRequest

@Composable
fun Home(viewModel: ImagesViewModel, navHostController: NavHostController) {
    val scrollState = rememberLazyGridState()
    val context = LocalContext.current

    val lazyGridState = LazyGridState
    val lifecycleOwner: LifecycleOwner
    LaunchedEffect(Unit) {
        //if (viewModel.readImages.value.isNullOrEmpty())
            //viewModel.getImages()
        viewModel.getImagesRoom()
    }

    val imageLoader = ImageLoader.Builder(context)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()

    val images =viewModel.imageslist
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
                items(30) {
                    val painter = rememberAsyncImagePainter(
                        model = "${directoryUpload + images[it].languageLable + "/" + images[it].image_upload}",
                        imageLoader = imageLoader,
                        filterQuality= FilterQuality.Low

                    )
                    ImageItem( painter = painter){}
                }
            }
        })
}


@Composable
fun ImageItem(painter: AsyncImagePainter, onClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                onClick()
            },

        ) {
        Image(
            painter = painter, contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale= ContentScale.Crop
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
                .height(200.dp).clip(RoundedCornerShape(6.dp))
                .background(brush)
        )
    }
}

