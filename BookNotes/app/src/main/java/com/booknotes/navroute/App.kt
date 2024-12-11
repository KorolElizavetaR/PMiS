package com.booknotes.navroute

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.booknotes.db.Book
import com.booknotes.db.BookViewModel
import com.booknotes.visuals.AddEditScreen
import com.booknotes.visuals.MainScreen

@Composable
fun App(viewModel: BookViewModel) {
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

sealed class Screen {
    object Main : Screen()
    data class AddEdit(val book: Book?) : Screen()
}
