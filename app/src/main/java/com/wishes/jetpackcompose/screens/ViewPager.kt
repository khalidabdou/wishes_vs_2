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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.wishes.jetpackcompose.R
import com.wishes.jetpackcompose.admob.showInterstitialAfterClick
import com.wishes.jetpackcompose.data.entities.Image
import com.wishes.jetpackcompose.utlis.AppUtil.getUriImage
import com.wishes.jetpackcompose.utlis.AppUtil.imagesBitmap
import com.wishes.jetpackcompose.utlis.AppUtil.shareImageUri
import com.wishes.jetpackcompose.utlis.Const.Companion.directoryUpload
import com.wishes.jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.wishes.jetpackcompose.utlis.loadPicture
import com.wishes.jetpackcompose.viewModel.ImagesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.random.Random


@OptIn(ExperimentalPagerApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun ViewPager(
    viewModel: ImagesViewModel,
    navController: NavController,
    page: Int?,
    route: String?,
    CatId: Int?
) {
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
        val apps = viewModel.apps.value
        HorizontalPager(
            modifier = Modifier.weight(9f),
            count = images.size,
            state = pagerState
        ) { page ->
            if (page % 21 == 0 && !apps.isNullOrEmpty()) {
                val app = apps.get(Random.nextInt(0, apps.size))
                Ad_app(app, context)

            } else {
                val url =
                    directoryUpload + images[page].languageLable + "/" + images[page].image_upload
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
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(6.dp)
        ) {

            Action(stringResource(R.string.fav), Icons.Default.Favorite) {
                viewModel.addToFav(images[pagerState.currentPage].id, 1)
                Toast.makeText(
                    context,
                    context.getString(R.string.add_fav),
                    Toast.LENGTH_LONG
                ).show()
                showInterstitialAfterClick(context)
            }
            /*Action("Download", Icons.Outlined.KeyboardArrowDown) {
                Toast.makeText(context,"Download success",Toast.LENGTH_LONG).show()
            }*/
            Action(stringResource(R.string.share_icon), Icons.Outlined.Share) {
                imagesBitmap[images[pagerState.currentPage].id]?.let {
                    val uri: Uri? = getUriImage(it, context)
                    shareImageUri(uri!!, context)
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
        Icon(icon, contentDescription = "", tint = MaterialTheme.colorScheme.onPrimaryContainer)
        Text(
            text = text, style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}