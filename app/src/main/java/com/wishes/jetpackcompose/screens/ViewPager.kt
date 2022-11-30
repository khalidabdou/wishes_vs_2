package com.wishes.jetpackcompose.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishes_jetpackcompose.data.entities.Image
import com.example.wishes_jetpackcompose.utlis.AppUtil.getUriImage
import com.example.wishes_jetpackcompose.utlis.AppUtil.imagesBitmap
import com.example.wishes_jetpackcompose.utlis.AppUtil.shareImageUri
import com.example.wishes_jetpackcompose.utlis.Const.Companion.directoryUpload
import com.example.wishes_jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.example.wishes_jetpackcompose.utlis.loadPicture
import com.example.wishes_jetpackcompose.viewModel.ImagesViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ringtones.compose.feature.admob.showInterstitialAfterClick
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalPagerApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun ViewPager(viewModel: ImagesViewModel, navController :NavController, page: Int?, route: String?, CatId: Int?) {
    val context = LocalContext.current
//    val imageLoader = ImageLoader.Builder(context)
//        .diskCache {
//            DiskCache.Builder()
//                .directory(context.cacheDir.resolve("image_cache"))
//                .maxSizePercent(0.02)
//                .build()
//        }
//        .build()


    when (route) {
        ImagesFrom.Fav.route -> {
            viewModel.getFavoritesRoom()
        }
        ImagesFrom.ByCat.route -> {
            viewModel.getByCatRoom(CatId!!)
        }
        ImagesFrom.Latest.route -> {
            viewModel.getImagesRoom()
        }
    }


    var images = emptyList<Image>()
    when (route) {
        ImagesFrom.Fav.route -> {
            images = viewModel.favoritesList
        }
        ImagesFrom.ByCat.route -> {
            images = viewModel.imagesByCategory
        }
        ImagesFrom.Latest.route -> {
            images = viewModel.imageslist
        }
    }
    SideEffect {
        //Toast.makeText(context,"re",Toast.LENGTH_LONG).show()
    }


    Column() {
        //var imageBitmap: Bitmap?=null
        val currentPage = page ?: 0
        val pagerState = rememberPagerState(currentPage)
        HorizontalPager(
            modifier = Modifier.weight(9f),
            count = images.size,
            state = pagerState
        ) { page ->
            //showInterstitialAfterClick(context)
            val url = directoryUpload + images[page].languageLable + "/" + images[page].image_upload
            //Toast.makeText(context,state.toString(),Toast.LENGTH_LONG).show()
//            val painter = rememberAsyncImagePainter(
//                model = directoryUpload + images[page].languageLable + "/" + images[page].image_upload,
//                imageLoader = imageLoader,
//                filterQuality = FilterQuality.Low
//
//            )
            val image = loadPicture(
                url = url,
                defaultImage = DEFAULT_RECIPE_IMAGE
            ).value

            Box(modifier = Modifier.fillMaxSize()) {
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(), contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                    imagesBitmap[images[page].id] = img
                    //imageBitmap=img
                }
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

            Action("Favorite",Icons.Default.Favorite) {
                viewModel.addToFav(images[pagerState.currentPage].id, 1)
                Toast.makeText(context,"add to favorites",Toast.LENGTH_LONG).show()
                showInterstitialAfterClick(context)
            }
            /*Action("Download", Icons.Outlined.KeyboardArrowDown) {
                Toast.makeText(context,"Download success",Toast.LENGTH_LONG).show()
            }*/
            Action("share", Icons.Outlined.Share) {
                imagesBitmap[images[pagerState.currentPage].id]?.let {
                    val uri: Uri?=getUriImage(it,context)
                    shareImageUri(uri!!,context)
                }
                showInterstitialAfterClick(context)
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