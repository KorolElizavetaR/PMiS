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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.catalog.ui.theme.Catalog56Theme
import com.catalog.ui_components.InfoScreen
import com.catalog.ui_components.MainScreen
import com.catalog.utils.ItemSaver
import com.catalog.utils.ListItem
import com.catalog.utils.Routes

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var item =
                rememberSaveable(stateSaver = ItemSaver) { mutableStateOf(ListItem("", "", "")) }
            val isDarkTheme = isSystemInDarkTheme()
            var darkTheme by rememberSaveable { mutableStateOf(isDarkTheme) }
            val context = LocalContext.current
            Catalog56Theme(darkTheme = darkTheme) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.MAIN_SCREEN.route
                ) {
                    composable(Routes.MAIN_SCREEN.route) {
                        MainScreen(context = context, darkTheme = darkTheme,
                            onThemeToggle = { darkTheme = !darkTheme }) { listItem ->
                            item.value =
                                ListItem(listItem.title, listItem.imageName, listItem.htmlName)
                            navController.navigate(Routes.INFO_SCREEN.route)
                        }
                    }
                    composable(Routes.INFO_SCREEN.route) {
                        InfoScreen(item = item.value!!, navController)
                    }
                }
            }
        }
    }
}