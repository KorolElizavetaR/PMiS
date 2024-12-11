package com.booknotes.visuals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.booknotes.db.Book

/* замени на rememberSavable */
@Composable
fun AddEditScreen(
    book: Book? = null,
    onSave: (Book) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(book?.title ?: "") }
    var author by remember { mutableStateOf(book?.author ?: "") }
    var note by remember { mutableStateOf(book?.note ?: "") }
    var imageUri by remember { mutableStateOf(book?.imageUri ?: "") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        TextField(value = author, onValueChange = { author = it }, label = { Text("Author") })
        TextField(value = note, onValueChange = { note = it }, label = { Text("Note") })
        TextField(
            value = imageUri,
            onValueChange = { imageUri = it },
            label = { Text("Image URI") })

        Row(modifier = Modifier.padding(top = 8.dp)) {
            Button(onClick = {
                onSave(Book(id = book?.id ?: 0, title, author, note, imageUri))
            }) {
                Text("Save")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}