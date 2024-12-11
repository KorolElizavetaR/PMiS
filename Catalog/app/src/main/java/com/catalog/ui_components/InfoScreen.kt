package com.catalog.ui_components

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import com.catalog.utils.ListItem

@Composable
fun InfoScreen(item: ListItem) {
    val configuration = LocalConfiguration.current
    Card(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        shape = RectangleShape
    ) {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                ContentSection(
                    imageName = item.imageName,
                    title = item.title,
                    htmlName = item.htmlName,
                    isLandscape = true
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ContentSection(
                    imageName = item.imageName,
                    title = item.title,
                    htmlName = item.htmlName,
                    isLandscape = false
                )
            }
        }
    }
}

@Composable
fun ContentSection(
    imageName: String,
    title: String,
    htmlName: String,
    isLandscape: Boolean
) {
    if (isLandscape) {
        AssetImage(
            imageName = imageName,
            contentDescription = title,
            modifier = Modifier
                .fillMaxHeight()
        )
    } else {
        AssetImage(
            imageName = imageName,
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 8.dp)
        )
    }
    HtmlLoader(htmlName = htmlName, modifier = Modifier.fillMaxWidth())
}


//@Composable
//fun InfoScreen(item: ListItem) {
//    val configuration = LocalConfiguration.current
//
//    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {


//@Composable
//fun HtmlLoader(htmlName: String) {
//    var backEnabled by rememberSaveable { mutableStateOf(false) }
//    var webView: WebView? = null
//    val context = LocalContext.current
//    val assetManger = context.assets
//    val inputStream = assetManger.open("html/$htmlName")
//    val size = inputStream.available()
//    val buffer = ByteArray(size)
//    inputStream.read(buffer)
//    val htmlString = String(buffer)
//    var prevPage: String? by rememberSaveable { mutableStateOf(null) }
//
//    AndroidView(modifier = Modifier
//        .fillMaxSize()
//        .padding(5.dp),
//        factory = { context ->
//            WebView(context).apply {
//                layoutParams = ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
//                webViewClient = object : WebViewClient() {
//                    override fun onPageStarted(
//                        view: WebView, url: String?,
//                        favicon: Bitmap?
//                    ) {
//                        backEnabled = view.canGoBack()
//                        prevPage = url
//                    }
//                }
//                settings.javaScriptEnabled = true
//
//                if (prevPage == null)
//                    loadData(htmlString, "text/html", "utf-8")
//                else
//                    loadUrl(prevPage!!)
//                webView = this
//            }
//        }, update = {
//            webView = it
//        })
//    BackHandler(enabled = backEnabled) {
//        webView?.goBack()
//    }
//}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HtmlLoader(
    htmlName: String,
    modifier: Modifier,
    viewModel: WebViewViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var backEnabled by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    val assetManger = context.assets
    val inputStream = assetManger.open("html/$htmlName")
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    val htmlString = String(buffer)

    var webView: WebView? = null

    AndroidView(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        factory = {
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(
                        view: WebView,
                        url: String?,
                        favicon: Bitmap?
                    ) {
                        backEnabled = view.canGoBack()
                    }
                }

                if (viewModel.webViewState.isEmpty) {
                    loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null)
                } else {
                    restoreState(viewModel.webViewState)
                }

                webView = this
            }
        },
        update = { view ->
            webView = view
            backEnabled = view.canGoBack()
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            webView?.saveState(viewModel.webViewState)
        }
    }

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }
}

class WebViewViewModel : ViewModel() {
    val webViewState = Bundle()
}