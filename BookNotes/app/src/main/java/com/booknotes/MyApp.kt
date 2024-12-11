package com.booknotes

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import com.booknotes.db.BookDatabase
import com.booknotes.db.BookRepository
import com.booknotes.db.BookViewModel
import com.booknotes.navroute.App

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.booknotes.db.Book
import com.booknotes.navroute.Screen
import com.booknotes.visuals.AddEditScreen
import com.booknotes.visuals.MainScreen

@Composable
fun MyApp(viewModel: BookViewModel) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Main) }
    var editableBook by remember { mutableStateOf<Book?>(null) }

    when (currentScreen) {
        is Screen.Main -> MainScreen(
            viewModel = viewModel,
            onAddClick = { currentScreen = Screen.AddEdit(null) },
            onEditClick = { book ->
                editableBook = book
                currentScreen = Screen.AddEdit(book)
            }
        )
        is Screen.AddEdit -> AddEditScreen(
            book = editableBook,
            onSave = { book ->
                if (editableBook == null) {
                    viewModel.addBook(book)
                } else {
                    viewModel.updateBook(book)
                }
                currentScreen = Screen.Main
            },
            onCancel = { currentScreen = Screen.Main }
        )
    }
}
