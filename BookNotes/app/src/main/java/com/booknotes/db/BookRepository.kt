package com.booknotes.db

class BookRepository(val bookDao: BookDAO) {
    fun getAllBooks() = bookDao.getAllBooks()
    suspend fun insertBook(book: Book) = bookDao.insertBook(book)
    suspend fun updateBook(book: Book) = bookDao.updateBook(book)
    suspend fun deleteBook(book: Book) = bookDao.deleteBook(book)
}