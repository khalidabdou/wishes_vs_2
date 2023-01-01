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
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.wishes.jetpackcompose.R
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
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf("specialonesteam@gmail.com"))
        i.putExtra(Intent.EXTRA_SUBJECT, context.packageName)
        i.putExtra(Intent.EXTRA_TEXT, "")
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
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
            "${context.getString(R.string.send_to)} \n https://play.google.com/store/apps/details?id=${context.packageName}"
        );
        context.startActivity(
            Intent.createChooser(
                shareIntent,
                context.getString(R.string.send_to)
            )
        )
    }

    fun openUrl(context: Context,url:String) {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

    fun shareImageUri(uri: Uri, context: Context,pack:String?) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)

            intent.putExtra(
                Intent.EXTRA_TEXT,
                "${context.resources.getString(R.string.send_to)} https://play.google.com/store/apps/details?id=${context.packageName}")

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (pack!=null)
            intent.`package`=pack
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