package com.catalog

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import com.catalog.ui_components.MainActivityContent

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MainActivityContent()
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