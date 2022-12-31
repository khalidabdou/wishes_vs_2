package com.wishes.jetpackcompose.screens.comp

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.wishes.jetpackcompose.R
import com.wishes.jetpackcompose.data.entities.LanguageApp
import com.wishes.jetpackcompose.utlis.AppUtil
import com.wishes.jetpackcompose.utlis.Const.Companion.directoryUpload
import com.wishes.jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.wishes.jetpackcompose.utlis.loadPicturetemmp
import com.wishes.jetpackcompose.viewModel.ImagesViewModel
import kotlin.random.Random

@Composable
fun DialogExit(viewModel: ImagesViewModel, context: Context, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            //showAlertDialog = false
            onDismiss()
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

@Composable
fun LanguagesDialog(
    languages: List<LanguageApp>?,
    onConfirm: (LanguageApp) -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        properties = DialogProperties(
            dismissOnClickOutside = true
        ),
        title = {
            Text(
                stringResource(R.string.select_language),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
            ) {
                items(languages!!.size) {
                    val url = directoryUpload + languages[it].label + "/" + languages[it].filename
                    val image: MutableState<Bitmap?>? = loadPicturetemmp(
                        url = url,
                        defaultImage = DEFAULT_RECIPE_IMAGE
                    )
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable {
                                onConfirm(languages[it])
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (image?.value == null) {
                            Image(
                                painter = painterResource(id = R.drawable.holder),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.FillWidth
                            )
                        } else
                            Image(
                                bitmap = image.value!!.asImageBitmap(), contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(6.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                        Text(
                            text = languages[it].name!!,
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                //onConfirm()
            }, modifier = Modifier) {

                Text(text = "Confirm")
            }
        },
        dismissButton = {

        },
        onDismissRequest = {}
    )
}

@Composable
fun WaitDialog(onDismiss: () -> Unit) {
    AlertDialog(
        title = { Text("Please wait") },
        text = {
            Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator()
            }

        },
        onDismissRequest = {},
        confirmButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    )
}