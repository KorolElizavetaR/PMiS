package com.catalog.ui_components

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.catalog.R
import com.catalog.utils.DrawerEvents
import com.catalog.utils.IdArrayList
import com.catalog.utils.ListItem
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
@Composable
fun MainScreen(context: Context, darkTheme: Boolean,
               onThemeToggle: () -> Unit, onClick: (ListItem)->Unit) {
    val dr_list = LocalContext.current.resources.getStringArray(R.array.drawer_list)
    val topBarTitle = remember { mutableStateOf(dr_list[0]) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val mainList = rememberSaveable {
        mutableStateOf(getListItemsByIndex(0, context))
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet() {
                DrawerMenu() { event ->
                    when (event) {
                        is DrawerEvents.OnItemClick -> {
                            topBarTitle.value = event.title
                            mainList.value = getListItemsByIndex(
                                event.index, context
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
                        title = topBarTitle.value,
                        drawerState = drawerState,
                        isDarkTheme = darkTheme,
                        onThemeToggle = onThemeToggle
                    )
                }
            ) { innerPadding ->
                MainListItemRows(
                    items = mainList.value,
                    modifier = Modifier.padding(innerPadding)
                ){listItem -> onClick(listItem)}
            }
        }
    )
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
                itemArray[1],
                itemArray[2]
            )
        )
    }
    return list
}