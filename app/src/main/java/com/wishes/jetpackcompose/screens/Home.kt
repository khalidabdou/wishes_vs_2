@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.wishes_jetpackcompose

import android.app.Activity
import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wishes.jetpackcompose.R
import com.wishes.jetpackcompose.admob.showInterstitialAfterClick
import com.wishes.jetpackcompose.data.entities.Page
import com.wishes.jetpackcompose.runtime.NavBarItems
import com.wishes.jetpackcompose.runtime.NavRoutes
import com.wishes.jetpackcompose.screens.ImagesFrom
import com.wishes.jetpackcompose.screens.NavigationDrawer
import com.wishes.jetpackcompose.screens.comp.AdBannerApp
import com.wishes.jetpackcompose.ui.theme.Inter
import com.wishes.jetpackcompose.utlis.AppUtil
import com.wishes.jetpackcompose.utlis.Const.Companion.directoryUpload
import com.wishes.jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.wishes.jetpackcompose.utlis.loadPicturetemmp
import com.wishes.jetpackcompose.viewModel.ImagesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoroutinesApi
@Composable
fun Home(viewModel: ImagesViewModel, navHostController: NavHostController) {
    val scrollState = rememberLazyGridState(0)
    val context = LocalContext.current
    val message = viewModel.message.collectAsState()
    val lazyGridState = LazyGridState
    var showAlertDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (viewModel.imageslist.isEmpty()) {
            viewModel.getImagesRoom()
        }

    }

    val images = viewModel.imageslist


    Surface() {
        BackHandler() {
            showAlertDialog = true
        }

        //create animations
        var navigateClick by remember { mutableStateOf(false) }
        val offSetAnim by animateDpAsState(
            targetValue = if (navigateClick) 300.dp else 0.dp,
            tween(1000)
        )
        val clipDp by animateDpAsState(
            targetValue = if (navigateClick) 60.dp else 0.dp,
            tween(1000)
        )
        val scaleAnim by animateFloatAsState(
            targetValue = if (navigateClick) 0.5f else 1.0f,
            tween(1000)
        )

        val rotate by animateFloatAsState(
            targetValue = if (navigateClick) 10f else 0f,
            tween(1000)
        )

        NavigationDrawer() {
            navigateClick = false
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .scale(scaleAnim)
                .offset(x = offSetAnim)
                .rotate(rotate)
                .clip(RoundedCornerShape(clipDp)),
            contentColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopBar(message.value) {
                    navigateClick = !navigateClick
                }
            },
            bottomBar = {
                BottomNavigationBar(navController = navHostController)
            },

            ) {
            LazyVerticalGrid(modifier = Modifier
                .fillMaxSize()
                .padding(it),
                state = scrollState,
                columns = GridCells.Fixed(2),
                content = {
                    if (images.isEmpty()) {
                        items(15) {
                            LoadingShimmerEffectImage()
                        }
                    } else {
                        items(images.size) {

                            val image: MutableState<Bitmap?>? = loadPicturetemmp(
                                url = directoryUpload + images[it].languageLable + "/" + images[it].image_upload,
                                defaultImage = DEFAULT_RECIPE_IMAGE
                            )
                            ImageItem(
                                image?.value?.asImageBitmap(),
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
                    }
                })

            if (showAlertDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showAlertDialog = false
                    },
                    title = {
                        Text(
                            stringResource(R.string.sure),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    text = {
                        val apps = viewModel.apps.value

                        if (!apps.isNullOrEmpty()) {
                            val app = apps.get(Random.nextInt(0, apps.size))
                            AdBannerApp(app)
                        }

                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                AppUtil.rateApp(context)

                            }) {
                            Text(stringResource(R.string.rate))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                //showAlertDialog=false
                                (context as Activity).finish()
                            }) {
                            Text(stringResource(R.string.quit))
                        }
                    },
                )
            }
        }
    }


}


@Composable
fun ImageItem(painter: ImageBitmap?, onClick: () -> Unit) {
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
        if (painter == null) {
            Image(
                painter = painterResource(id = R.drawable.holder), contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        } else
            Image(
                bitmap = painter, contentDescription = null,
                modifier = Modifier.fillMaxSize(),
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


@Composable
fun BottomNavigationBar(navController: NavHostController) {

    Column {
        BottomNavigation(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            NavBarItems.BarItems.forEach { navItem ->
                BottomNavigationItem(
                    selected = currentRoute == navItem.route,
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                        }
                    },

                    icon = {
                        Icon(
                            imageVector = navItem.image,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentDescription = navItem.title
                        )
                    },
                    label = {
                        Text(
                            text = navItem.title,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = Inter
                            )
                        )
                    },
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onDrawer: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )

    TopAppBar(
        modifier = Modifier.clickable { onDrawer() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = Inter
                )
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onDrawer()
                }
            ) {
                Icon(
                    imageVector =  Icons.Default.Settings,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = null,
                    modifier = Modifier.rotate(angle)
                )
            }
        },
        actions = {


            /*if (isMoreOptionPopupShowed) {
                MoreOptionPopup(
                    options = listOf(
                        stringResource(id = R.string.rate),
                        stringResource(id = R.string.rate),
                        stringResource(id = R.string.share),

                        ),
                    onDismissRequest = {
                        isMoreOptionPopupShowed = false
                    },
                    onClick = { i ->
                        when (i) {
                            0 -> {
                                AppUtil.openStore(context)
                            }
                            1 -> {

                            }
                            2 -> {
                                AppUtil.share(context)
                            }

                        }
                    },
                    modifier = Modifier
                        .padding(8.dp)
                )
            }*/
        }
    )
}


@Composable
fun MoreOptionPopup(
    options: List<String>,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onClick: (Int) -> Unit
) {

    Popup(
        alignment = Alignment.BottomCenter,
        onDismissRequest = onDismissRequest
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = modifier
        ) {
            options.forEachIndexed { i, label ->
                Row {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = Inter
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                onDismissRequest()
                                onClick(i)
                            }
                    )
                }

            }
        }
    }
}

