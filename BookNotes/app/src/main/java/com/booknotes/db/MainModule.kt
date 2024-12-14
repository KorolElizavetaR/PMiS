//package com.booknotes.db
//
//import android.app.Application
//import androidx.room.Room
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import jakarta.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object MainModule {
//    @Provides
//    @Singleton
//    fun provideBookDatabase(app: Application): BookDatabase {
//        return Room.databaseBuilder(
//            app,
//            BookDatabase::class.java,
//            "db.db"
//        ).createFromAsset("db/db.db").build()
//    }
//}