package com.example.wishes_jetpackcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import com.example.wishes_jetpackcompose.utlis.Const.Companion.directoryUpload
import com.example.wishes_jetpackcompose.viewModel.ImagesViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ViewPager(viewModel: ImagesViewModel, page: String?) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()


    LaunchedEffect(Unit) {
        viewModel.getImagesRoom()
    }
    val images = viewModel.imageslist
    val pagerState = rememberPagerState(page!!.toInt())

    Column() {
        HorizontalPager(
            modifier = Modifier.weight(9f),
            count = 30,
            state = pagerState
        ) { page ->
            //Toast.makeText(context,state.toString(),Toast.LENGTH_LONG).show()
            val painter = rememberAsyncImagePainter(
                model = directoryUpload + images[page].languageLable + "/" + images[page].image_upload,
                imageLoader = imageLoader,
                filterQuality = FilterQuality.Low

            )
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painter, contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {

            Action("Favorite",Icons.Outlined.Favorite){
                viewModel.addToFav(images[0].id,1)
            }
            Action("Download",Icons.Outlined.KeyboardArrowDown){

            }
            Action("share",Icons.Outlined.Share){

            }
        }
    }
}

@Composable
fun Action(text: String, icon: ImageVector, onClickAction: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClickAction()
        }) {
        Icon(icon, contentDescription = "")
        Text(
            text = text, style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}