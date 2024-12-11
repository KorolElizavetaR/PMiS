package com.booknotes

import android.app.Application
import androidx.room.Room
import com.booknotes.db.BookDatabase

@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    @Provides
    @Singleton
    @Named("PersistentBookDatabase")
    fun providePersistentBookDatabase(app: Application): BookDatabase {
        return Room.databaseBuilder(
            app,
            BookDatabase::class.java,
            "book_database.db"
        ).build()
    }
}