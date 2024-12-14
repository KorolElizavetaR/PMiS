package com.catalog.ui_components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.catalog.MainViewModel
import com.catalog.utils.DrawerEvents
import com.catalog.utils.ListItem
import kotlinx.coroutines.launch

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
//@Composable
//fun MainScreen(
//    mainViewModel: MainViewModel = hiltViewModel(), onClick: (ListItem) -> Unit
//) {
//    val topBarTitle = rememberSaveable { mutableStateOf("Бизнес Литература") }
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    val mainlist = mainViewModel.mainList
//
//    LaunchedEffect(Unit) {
//        mainViewModel.getAllItemsByCategory(topBarTitle.value)
//    }
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet(drawerShape = RectangleShape) {
//                DrawerMenu() { event ->
//                    when (event) {
//                        is DrawerEvents.OnItemClick -> {
//                            topBarTitle.value = event.title
//                            mainViewModel.getAllItemsByCategory(event.title)
//                        }
//                    }
//                    scope.launch {
//                        drawerState.close()
//                    }
//                }
//            }
//        },
//        content = {
//            Scaffold(
//                topBar = {
//                    MainTopBar(title = topBarTitle.value, drawerState = drawerState){
//                        topBarTitle.value = "Избранные"
//                        mainViewModel.getFavorites()
//                    }
//                }
//            ) { innerPadding ->
//                MainListItemRows(
//                    items = mainlist.value,
//                    modifier = Modifier.padding(innerPadding),
//                ) { listItem -> onClick(listItem) }
//            }
//        }
//
//    )
//}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    onClick: (ListItem)->Unit
) {
    val topBarTitle = rememberSaveable { mutableStateOf("Бизнес Литература") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val mainList = mainViewModel.mainList

    LaunchedEffect(topBarTitle.value) {
        if (topBarTitle.value == "Избранное") {
            mainViewModel.getFavorites()
        } else {
            mainViewModel.getAllItemsByCategory(topBarTitle.value)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerMenu { event ->
                    when(event) {
                        is DrawerEvents.OnItemClick -> {
                            topBarTitle.value=event.title
                            mainViewModel.getAllItemsByCategory(event.title)
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
                    MainTopBar(title = topBarTitle.value, drawerState = drawerState) {
                        topBarTitle.value = "Избранное"
                        mainViewModel.getFavorites()
                    }
                }
            ) { innerPadding ->
                MainListItemRows(
                    items = mainList.value,
                    modifier = Modifier.padding(innerPadding),
                ) { listItem -> onClick(listItem) }
            }

        }
    )
}