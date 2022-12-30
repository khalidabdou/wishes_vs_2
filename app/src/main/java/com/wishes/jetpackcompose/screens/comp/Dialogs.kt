package com.wishes.jetpackcompose.screens.comp

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.wishes.jetpackcompose.R
import com.wishes.jetpackcompose.data.entities.LanguageApp
import com.wishes.jetpackcompose.utlis.NetworkResults
import com.wishes.jetpackcompose.viewModel.ImagesViewModel



@Composable
fun LanguagesDialog(
    viewModel: ImagesViewModel,
    onSelect: (LanguageApp) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        properties = DialogProperties(
            dismissOnClickOutside = true
        ),
        title = {
            Text(stringResource(R.string.select_language))
        },
        text = {


        },
        confirmButton = {
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        },
        onDismissRequest = {}
    )
}