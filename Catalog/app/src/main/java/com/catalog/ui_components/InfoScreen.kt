package com.catalog.ui_components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.catalog.utils.ListItem
import com.catalog.utils.Routes

@Composable
fun InfoScreen(item: ListItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            //  .padding(5.dp)
            .background(color = MaterialTheme.colorScheme.background),
       // shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Button(
                    onClick = { navController.navigate(route = Routes.MAIN_SCREEN.route) }
                ) {
                    Text(text = "Back")
                }
            }
            HtmlLoader(htmlName = item.htmlName)
        }
    }
}

@Composable
fun HtmlLoader(htmlName: String) {
    val context = LocalContext.current
    val assetManger = context.assets
    val inputStream = assetManger.open("html/$htmlName")
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    val htmlString = String(buffer)
    AndroidView(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp),
        factory = {
            WebView(it).apply {
                webViewClient = WebViewClient()
                loadData(htmlString, "text/html", "utf-8")
            }
        })
}