package com.catalog.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.catalog.utils.ListItem

@Dao
interface Dao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ListItem)
    @Delete
    suspend fun deleteitem(item: ListItem)
    @Query("select * from main where category like :cat")
    suspend fun getAllItemsByCategory(cat:String): List<ListItem>
}