package com.booknotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.booknotes.db.BookDatabase
import com.booknotes.db.BookRepository
import com.booknotes.db.BookViewModel
import com.booknotes.ui.theme.BookNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the database and repository
        val db = Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java,
            "book_db"
        ).build()

        val repository = BookRepository(db.bookDao())
        val bookViewModel = BookViewModel(repository)
        enableEdgeToEdge()
        setContent {
            BookNotesTheme {
                MyApp(bookViewModel)
            }
        }
    }
}
