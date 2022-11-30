package com.example.wishes_jetpackcompose

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import com.example.wishes_jetpackcompose.runtime.NavRoutes
import com.example.wishes_jetpackcompose.utlis.Const.Companion.directoryUploadCat
import com.example.wishes_jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.example.wishes_jetpackcompose.utlis.loadPicture
import com.example.wishes_jetpackcompose.viewModel.ImagesViewModel


@Composable
fun Categories(viewModel: ImagesViewModel, navHostController: NavHostController) {
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current


    var cats = viewModel.categories.value

    LaunchedEffect(Unit) {
        viewModel.getCategoriesRoom()
    }
    val categories = viewModel.categoriesList

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (categories.isEmpty()) {
            item {
                repeat(10) {
                    LoadingShimmerEffect()
                }
            }
        } else {
            items(categories.size) {
                val category = categories[it]
//                val imageLoader = ImageLoader.Builder(context)
//                    .diskCache {
//                        DiskCache.Builder()
//                            .directory(context.cacheDir.resolve("image_cache"))
//                            .maxSizePercent(0.02)
//                            .build()
//                    }
//                    .build()

//                val painter = rememberAsyncImagePainter(
//                    model = directoryUploadCat + category.image,
//                    imageLoader = imageLoader,
//                    contentScale = ContentScale.Inside
//                )
                val image = loadPicture(
                    url = directoryUploadCat + category.image,
                    defaultImage = DEFAULT_RECIPE_IMAGE
                ).value
                image?.let { img ->
                    ItemCategory(category.name, img.asImageBitmap()) {
                        navHostController.navigate(NavRoutes.ByCat.route + "/" + category.id)
                    }
                }
            }
        }
    }
}


@Composable
fun ItemCategory(text: String, painter: ImageBitmap, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },

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
                bitmap = painter, contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
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