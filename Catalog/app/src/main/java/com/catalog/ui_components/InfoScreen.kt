package com.catalog.ui_components

import android.content.res.Configuration
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.catalog.utils.ListItem

//@Composable
//fun InfoScreen(item: ListItem) {
//    val configuration = LocalConfiguration.current
//    Card(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = MaterialTheme.colorScheme.background),
//        shape = RectangleShape
//    ) {
//        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Row(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                AssetImage(
//                    imageName = item.imageName,
//                    contentDescription = item.title,
//                    modifier = Modifier
//                        .fillMaxHeight()
//                )
//                HtmlLoader(htmlName = item.htmlName)
//            }
//        } else {
//            Column(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                AssetImage(
//                    imageName = item.imageName,
//                    contentDescription = item.title,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(300.dp)
//                        .padding(bottom = 8.dp)
//                )
//                HtmlLoader(
//                    htmlName = item.htmlName
//                )
//            }
//        }
//    }
//}

@Composable
fun InfoScreen (item: ListItem) {
    val configuration = LocalConfiguration.current

    val height: Dp = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        0.dp
    } else {
        400.dp
    }

    Card(
        modifier = Modifier.fillMaxSize().padding(5.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        AssetImage(
            imageName = item.imageName,
            contentDescription = item.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )
        HtmlLoader(htmlName = item.htmlName)
    }
}


@Composable
fun HtmlLoader(htmlName: String, modifier: Modifier = Modifier) {
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    val context = LocalContext.current
    val assetManger = context.assets
    val inputStream = assetManger.open("html/$htmlName")
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    val htmlString = String(buffer)
    var prevPage: String? by rememberSaveable { mutableStateOf(null) }

    AndroidView(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(
                        view: WebView, url: String?,
                        favicon: Bitmap?
                    ) {
                        backEnabled = view.canGoBack()
                        prevPage = url
                    }
                }
                settings.javaScriptEnabled = true

                if (prevPage == null)
                    loadData(htmlString, "text/html", "utf-8")
                else
                    loadUrl(prevPage!!)
                webView = this
            }
        }, update = {
            webView = it
        })
    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }
}