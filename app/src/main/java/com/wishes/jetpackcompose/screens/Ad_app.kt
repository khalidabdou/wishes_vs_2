package com.wishes.jetpackcompose.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wishes.jetpackcompose.R
import com.wishes.jetpackcompose.data.entities.App
import com.wishes.jetpackcompose.utlis.AppUtil
import com.wishes.jetpackcompose.utlis.DEFAULT_RECIPE_IMAGE
import com.wishes.jetpackcompose.utlis.loadPicture


@Composable
fun Ad_app(app: App,context:Context) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.padding(30.dp),
            text = "ad")
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val image = loadPicture(
                url = app.image,
                defaultImage = DEFAULT_RECIPE_IMAGE
            ).value

            image?.let {
                Image(
                    bitmap = it.asImageBitmap(), contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = "${app.title}",
                modifier = Modifier.padding(9.dp), textAlign = TextAlign.Center)
            OutlinedButton(
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = {
                    AppUtil.openUrl(context,app.url)
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
