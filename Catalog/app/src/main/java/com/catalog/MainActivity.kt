package com.catalog

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.catalog.ui.theme.Catalog56Theme
import com.catalog.ui_components.InfoScreen
import com.catalog.ui_components.MainScreen
import com.catalog.utils.ItemSaver
import com.catalog.utils.ListItem
import com.catalog.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //clearDatabase(this)
        setContent {
            var item= rememberSaveable(stateSaver = ItemSaver) {
                mutableStateOf( ListItem(id = 0, title = "", imageName = "",
                    htmlName = "", isfav = false, category = ""))
            }
            val navController = rememberNavController()
            Catalog56Theme() {
                NavHost(
                    navController = navController,
                    startDestination = Routes.MAIN_SCREEN.route
                ) {
                    composable(Routes.MAIN_SCREEN.route) {
                        MainScreen() { listItem ->item.value =ListItem(listItem.id,
                            listItem.title,
                            listItem.imageName,
                            listItem.htmlName,
                            listItem.isfav,
                            listItem.category)
                            navController.navigate(Routes.INFO_SCREEN.route)
                        }
                    }
                    composable(Routes.INFO_SCREEN.route) {
                        InfoScreen(item = item.value!!) // navController
                    }
                }
            }
        }
    }
}

//fun clearDatabase(context: Context) {
//    val dbPath = context.getDatabasePath("info.db")
//    if (dbPath.exists()) {
//        if (dbPath.delete()) {
//            Log.d("DB_CLEAR", "База данных успешно удалена.")
//        } else {
//            Log.e("DB_CLEAR", "Не удалось удалить базу данных.")
//        }
//    } else {
//        Log.d("DB_CLEAR", "База данных не найдена.")
//    }
//}
//
//
//fun clearAllDatabases(context: Context) {
//    val dbDir = context.getDatabasePath("info.db").parentFile
//    if (dbDir != null && dbDir.exists()) {
//        dbDir.deleteRecursively()
//        Log.d("DB_CLEAR", "Все базы данных удалены.")
//    } else {
//        Log.d("DB_CLEAR", "Папка базы данных не найдена.")
//    }
//}fun clearAppData(context: Context) {
//    try {
//        val runtime = Runtime.getRuntime()
//        runtime.exec("pm clear ${context.packageName}")
//        Log.d("APP_CLEAR", "Данные приложения очищены.")
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}