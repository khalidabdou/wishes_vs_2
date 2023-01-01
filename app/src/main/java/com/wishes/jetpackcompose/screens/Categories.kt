package com.example.wishes_jetpackcompose

import android.graphics.Bitmap
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.wishes.jetpackcompose.R
import com.wishes.jetpackcompose.runtime.NavRoutes
import com.wishes.jetpackcompose.utlis.Const.Companion.directoryUploadCat
import com.wishes.jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.wishes.jetpackcompose.utlis.loadPicture
import com.wishes.jetpackcompose.viewModel.ImagesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun Categories(viewModel: ImagesViewModel, navHostController: NavHostController) {
    val scaffoldState = rememberScaffoldState()

    val context = LocalContext.current

    LaunchedEffect(viewModel.categoriesList) {
        if (viewModel.categoriesList.isEmpty()) {
            viewModel.getCategoriesRoom()
            //Toast.makeText(context, "em", Toast.LENGTH_SHORT).show()
        }

    }


    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
    ) {
        if (viewModel.categoriesList.isEmpty()) {
            items(10) {
                LoadingShimmerEffect()
            }
        } else {
            items(viewModel.categoriesList.size) {
                val category = viewModel.categoriesList[it]
                val image: MutableState<Bitmap?>? = loadPicture(
                    url = directoryUploadCat + category.image,
                    defaultImage = DEFAULT_RECIPE_IMAGE
                )
                ItemCategory(category.name, image?.value?.asImageBitmap()) {
                    navHostController.navigate(NavRoutes.ByCat.route + "/" + category.id)
                }
            }
        }
    }
}


@Composable
fun ItemCategory(text: String, painter: ImageBitmap?, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(6.dp)
                .size(100.dp)
                .clip(CircleShape)

        ) {
            if (painter == null)
                Icon(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    tint = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                )
            else
                androidx.compose.foundation.Image(
                    bitmap = painter, contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
        }

        Text(
            text = "$text",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(6.dp)

        )
    }
}

@Composable
fun LoadingShimmerEffect() {
    //These colors will be used on the brush. The lightest color should be in the middle
    val gradient = listOf(
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f), //darker grey (90% opacity)
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), //lighter grey (30% opacity)
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f)
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(brush)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Spacer(
                modifier = Modifier
                    .height(15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(fraction = 0.7f)
                    .background(brush)
            )
        }
    }
}