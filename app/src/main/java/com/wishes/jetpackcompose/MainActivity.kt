package com.wishes.jetpackcompose


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.AdSize
import com.ringtones.compose.feature.admob.AdvertViewAdmob
import com.ringtones.compose.feature.admob.AdvertViewFAN
import com.wishes.jetpackcompose.runtime.NavRoutes
import com.wishes.jetpackcompose.runtime.NavigationHost
import com.wishes.jetpackcompose.ui.theme.Wishes_jetpackComposeTheme
import com.wishes.jetpackcompose.viewModel.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel: ImagesViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wishes_jetpackComposeTheme {

                // A surface container using the 'background' color from the theme
                val context = LocalContext.current
                navController = rememberNavController()


//                BackHandler {
//                    showAlertDialog = true
//                }

                Surface() {
                    Column(modifier = Modifier.padding()) {
                        Box(modifier = Modifier.weight(1f)) {
                            NavigationHost(navController = navController, viewModel)
                        }
                        if (currentRoute(navController) != NavRoutes.Splash.route)
                                AdvertViewAdmob()

                    }

                }


            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}





