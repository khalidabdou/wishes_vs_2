package com.example.wishes_jetpackcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.wishes_jetpackcompose.ui.theme.Wishes_jetpackComposeTheme
import androidx.compose.material3.Card
import androidx.compose.ui.platform.LocalContext
import com.example.wishes_jetpackcompose.ui.theme.Inter
import com.example.wishes_jetpackcompose.utlis.AppUtil
import java.util.Locale.Category

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wishes_jetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentColor = MaterialTheme.colorScheme.background,
                    topBar = {
                        TopBar()
                    },

                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(it)) {
                        LazyRow(content = {
                            items(10) { Category() }
                        })
                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    val context = LocalContext.current
    var isMoreOptionPopupShowed by remember { mutableStateOf(false) }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = { Text(
            text = "wishes 2022",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = Inter
            )
        )},
        navigationIcon = {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_setting),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }

            if (isMoreOptionPopupShowed) {
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
                            1->{

                            }
                            2->{
                                AppUtil.share(context)
                            }

                        }
                    },
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
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

@Composable
fun Category(){
    Card(modifier = Modifier
        .size(90.dp)
        .background(MaterialTheme.colorScheme.onSurface)) {
        Image(painter = painterResource(id = R.drawable.ic_setting), contentDescription = "")
    }
}