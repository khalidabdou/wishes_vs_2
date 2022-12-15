package com.wishes.jetpackcompose.screens.comp

import android.net.http.HttpResponseCache.install
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wishes.jetpackcompose.R
import com.wishes.jetpackcompose.data.entities.App
import com.wishes.jetpackcompose.utlis.AppUtil
import com.wishes.jetpackcompose.utlis.AppUtil.openUrl
import com.wishes.jetpackcompose.utlis.Const
import com.wishes.jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.wishes.jetpackcompose.utlis.loadPicture

@Composable
fun AdBannerApp(app:App?) {
    val context= LocalContext.current
    if (app != null)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background).padding(5.dp)
               ,
            horizontalArrangement = Arrangement.SpaceEvenly,


        ) {
            val image = loadPicture(
                url = app.image,
                defaultImage = DEFAULT_RECIPE_IMAGE
            ).value
            Text(
                text = "Ad",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            image?.let {
                Image(
                    bitmap = it.asImageBitmap(), contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(6.dp))
            Column() {
                Text(text = app.title,
                    color = MaterialTheme.colorScheme.onBackground)
                Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        onClick = {
                            openUrl(context,app.url)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.install),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

            }

        }
}