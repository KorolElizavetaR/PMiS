package com.booknotes.db

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BookViewModel(val repository: BookRepository) : ViewModel() {
    val books = mutableStateListOf<Book>()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        books.clear()
        books.addAll(repository.getAllBooks())
    }

    fun addBook(book: Book) {
        viewModelScope.launch {
            repository.insertBook(book)
            loadBooks()
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
            loadBooks()
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
            loadBooks()
        }
    }
}
