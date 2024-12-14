package com.booknotes.db

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel (application: Application) : AndroidViewModel(application) {
    private val readData: LiveData<List<Book>>
    private val bookDao: BookDAO

    init {
        bookDao = BookDatabase.getDatabase(application).bookDao()
        readData = bookDao.getAllBooks()
    }

    fun addBook(book: Book) = viewModelScope.launch {
        bookDao.insertBook(book)
        //loadBooks()
    }

    fun updateBook(book: Book) = viewModelScope.launch{
        bookDao.updateBook(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch{
        bookDao.deleteBook(book)
    }
}
