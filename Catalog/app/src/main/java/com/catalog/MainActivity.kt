package com.catalog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.catalog.ui.theme.Catalog56Theme
import com.catalog.ui_components.DrawerMenu
import com.catalog.ui_components.MainListItem
import com.catalog.ui_components.MainTopBar
import com.catalog.utils.DrawerEvents
import com.catalog.utils.IdArrayList
import com.catalog.utils.ListItem
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Catalog56Theme {
                val topBarTitle = remember { mutableStateOf("Азиатские кошки") }
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val mainList = remember {
                    mutableStateOf(getListItemsByIndex(0, this))
                }
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            DrawerMenu() { event ->
                                when (event) {
                                    is DrawerEvents.OnItemClick -> {
                                        topBarTitle.value = event.title
                                        mainList.value = getListItemsByIndex(
                                            event.index, this@MainActivity
                                        )
                                    }
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        }
                    },
                    content = {
                        Scaffold(
                            topBar = {
                                MainTopBar(
                                    title = topBarTitle.value, drawerState
                                    = drawerState
                                )
                            }
                        ) { innerPadding ->
                            LazyColumn(
                                modifier =
                                Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize()
                            ) {
                                items(mainList.value) { item ->
                                    MainListItem(item = item)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
private fun getListItemsByIndex(index: Int, context: Context): List<ListItem> {
    val list = ArrayList<ListItem>()
    val arrayList = context.resources.getStringArray(IdArrayList.listId[index])
    arrayList.forEach { item ->
        val itemArray = item.split("|")
        list.add(
            ListItem(
                itemArray[0],
                itemArray[1]
            )
        )
    }
    return list
}