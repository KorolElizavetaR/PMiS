package com.booknotes.visuals

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.booknotes.db.Book
import com.booknotes.db.BookViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: BookViewModel, onAddClick: () -> Unit, onEditClick: (Book) -> Unit) {
    val books = viewModel.books

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) {
        LazyColumn {
            items(books) { book ->
                BookItem(book, onEditClick) {
                    viewModel.deleteBook(book)
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onEditClick: (Book) -> Unit, onDeleteClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Title: ${book.title}")
            Text("Author: ${book.author}")
            Text("Note: ${book.note}")
        }
        IconButton(onClick = { onEditClick(book) }) {
            Icon(Icons.Default.Edit, contentDescription = "Edit")
        }
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}