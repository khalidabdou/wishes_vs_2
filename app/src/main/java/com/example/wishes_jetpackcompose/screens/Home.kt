package com.example.wishes_jetpackcompose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import com.example.wishes_jetpackcompose.runtime.NavRoutes

@Composable
fun Home(navHostController: NavHostController) {
    val scrollState = rememberLazyGridState()
    val context= LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()

    val painter = rememberAsyncImagePainter(
        model = "http://wishesyougood.com/category/4650281_27188.gif",
        imageLoader = imageLoader,
        contentScale = ContentScale.Inside
    )
    LazyVerticalGrid(
        state = scrollState,
        columns = GridCells.Adaptive(128.dp),
        // content padding

        content = {
            items(20) { Image(painter){
                //Toast.makeText(context,"ksd",Toast.LENGTH_LONG).show()
                navHostController.navigate(NavRoutes.ViewPager.route)
            } }
        })
}


//@Composable
//fun Category(onClick: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .size(90.dp)
//            .background(MaterialTheme.colorScheme.onSurface)
//    ) {
//        Image(painter = painterResource(id = R.drawable.ic_setting), contentDescription = "")
//    }
//}

@Composable
fun Image(painter: AsyncImagePainter, onClick: () -> Unit) {
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
            modifier = Modifier.fillMaxSize()
        )

    }
}


