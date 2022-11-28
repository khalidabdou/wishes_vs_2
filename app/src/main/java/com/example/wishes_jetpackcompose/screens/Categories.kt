package com.example.wishes_jetpackcompose

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import com.example.wishes_jetpackcompose.utlis.Const.Companion.directoryUploadCat
import com.example.wishes_jetpackcompose.viewModel.ImagesViewModel


@Composable
fun Categories() {
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current
    val viewModel: ImagesViewModel = hiltViewModel()

    var cats = viewModel.categories.value

    LaunchedEffect(scaffoldState) {
        if (viewModel.categories.value?.data?.listCategory.isNullOrEmpty())
            try {
                viewModel.getCategories()
            } catch (ex: Exception) {
                Toast.makeText(context, "Please try again", Toast.LENGTH_LONG).show()
            }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (viewModel.categories.value?.data?.listCategory.isNullOrEmpty()) {
            item {
                repeat(5) {
                    LoadingShimmerEffect()
                }
            }
        } else {

            items(viewModel.categories.value?.data?.listCategory!!.size) {
                val ca = viewModel.categories.value?.data?.listCategory!![it]
                val imageLoader = ImageLoader.Builder(context)
                    .diskCache {
                        DiskCache.Builder()
                            .directory(context.cacheDir.resolve("image_cache"))
                            .maxSizePercent(0.02)
                            .build()
                    }
                    .build()

                val painter = rememberAsyncImagePainter(
                    model = "${directoryUploadCat + ca.image}",
                    imageLoader = imageLoader,
                    contentScale = ContentScale.Inside
                )
                ItemCategory(ca.name, painter)

            }
        }

    }

}


@Composable
fun ItemCategory(text: String, painter: AsyncImagePainter) {
    Row(
        modifier = Modifier.padding(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            val context = LocalContext.current
            androidx.compose.foundation.Image(
                painter = painter, contentDescription = null,
                contentScale=ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        androidx.compose.material3.Text(
            text = "$text",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun LoadingShimmerEffect() {
    //These colors will be used on the brush. The lightest color should be in the middle
    val gradient = listOf(
        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f), //darker grey (90% opacity)
        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), //lighter grey (30% opacity)
        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
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
    ShimmerGridItem(brush = brush)
}

@Composable
fun ShimmerGridItem(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp), verticalAlignment = Alignment.Top
    ) {
        Spacer(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(brush)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 0.5f)
                    .background(brush)
            )

            Spacer(modifier = Modifier.height(10.dp)) //creates an empty space between
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 0.7f)
                    .background(brush)
            )

            Spacer(modifier = Modifier.height(10.dp)) //creates an empty space between
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 0.9f)
                    .background(brush)
            )
        }
    }
}