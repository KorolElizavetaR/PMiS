package com.booknotes.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val note: String,
    val imageUri: String
)