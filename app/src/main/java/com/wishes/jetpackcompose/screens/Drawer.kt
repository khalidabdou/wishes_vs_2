package com.wishes.jetpackcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wishes.jetpackcompose.admob.showInterstitialAfterClick
import com.wishes.jetpackcompose.utlis.AppUtil


@Composable
fun NavigationDrawer(onClick: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Wishes",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            ItemDrawer("Invite", Icons.Default.Person) {
                AppUtil.share(context)
                showInterstitialAfterClick(context)
            }
            ItemDrawer("Rate", Icons.Default.Star) {
                AppUtil.rateApp(context)
                showInterstitialAfterClick(context)
            }
            ItemDrawer("Our Apps", Icons.Default.List) {
                AppUtil.openStore("https://play.google.com/store/apps/developer?id=laik", context)
                showInterstitialAfterClick(context)
            }
            ItemDrawer("Feedback", Icons.Default.Email) {
                AppUtil.sendEmail(context)
                showInterstitialAfterClick(context)
            }
            ItemDrawer("Privacy Policy", Icons.Default.Info) {
                AppUtil.openStore("https://stickersapi.specialones.online", context)
                showInterstitialAfterClick(context)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()

                    , horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Icon(
                    Icons.Default.ArrowBack,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary).clickable {
                            onClick()
                        }
                )
            }
        }
    }
}

@Composable
fun ItemDrawer(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Icon(icon, contentDescription = "")
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }

}