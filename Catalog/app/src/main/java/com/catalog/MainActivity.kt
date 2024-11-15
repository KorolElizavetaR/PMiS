package com.catalog

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.catalog.ui.theme.Catalog56Theme
import com.catalog.utils.MainScreen

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            var darkTheme by rememberSaveable { mutableStateOf(isDarkTheme) }
            val context = LocalContext.current
            Catalog56Theme(darkTheme = darkTheme) {
                MainScreen(context = this, darkTheme = darkTheme,
                    onThemeToggle = { darkTheme = !darkTheme })
            }
        }
    }
}

//@RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
//private fun getListItemsByIndex(index: Int, context: Context): List<ListItem> {
//    val list = ArrayList<ListItem>()
//    val arrayList = context.resources.getStringArray(IdArrayList.listId[index])
//    arrayList.forEach { item ->
//        val itemArray = item.split("|")
//        list.add(
//            ListItem(
//                itemArray[0],
//                itemArray[1]
//            )
//        )
//    }
//    return list
//}