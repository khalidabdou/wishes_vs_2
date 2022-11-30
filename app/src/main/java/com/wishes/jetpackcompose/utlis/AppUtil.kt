package com.wishes.jetpackcompose.utlis

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.example.wishes_jetpackcompose.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object AppUtil {

    val IS_RINGTONE = true


    fun Any?.toast(context: Context, length: Int = Toast.LENGTH_SHORT) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, this.toString(), length).show()
        }
    }

    @SuppressLint("ComposableNaming")
    @Composable
    fun Any?.toast(length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(LocalContext.current, this.toString(), length).show()
    }

    fun rateApp(context: Context) {
        val appPackageName: String =
            context.packageName // getPackageName() from Context or Activity object

        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    fun sendEmail(context: Context) {

    }

     fun openStore(url: String,context: Context) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$context.packageName")
                )
            )
        }
    }


    fun share(context: Context) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Enjoy this app \n https://play.google.com/store/apps/details?id=${context.packageName}"
        );
        context.startActivity(
            Intent.createChooser(
                shareIntent,
                context.getString(R.string.send_to)
            )
        )
    }

    fun shareImageUri(uri: Uri, context: Context) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)

            intent.putExtra(
                Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=${context.packageName}")

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        try {
            context!!.startActivity(intent)
        } catch (ex: Exception) {
            Toast.makeText(context, "Error Please Try Again", Toast.LENGTH_LONG).show()
        }

    }
    fun getUriImage(image: Bitmap,context: Context): Uri? {
        //quote?.id?.let { quotesViewmodel.incShareQuote(it) }
        val imagesFolder = File(context.cacheDir, "images")
        var uri: Uri? = null
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "shared_image.png")
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            uri =
                FileProvider.getUriForFile(
                    context, context.packageName + ".fileProvider",
                    file
                )
        } catch (e: IOException) {
            Log.d(
                "TAG",
                "IOException while trying to write file for sharing: " + e.message
            )

        }
        return uri
    }

    val imagesBitmap = HashMap<Int,Bitmap>()




}