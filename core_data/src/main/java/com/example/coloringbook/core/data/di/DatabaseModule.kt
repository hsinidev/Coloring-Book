package com.example.coloringbook.core.data.di

import android.content.Context
import androidx.room.Room
import com.example.coloringbook.core.data.database.ColoringDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ColoringDatabase {
        return Room.databaseBuilder(
            context,
            ColoringDatabase::class.java,
            "coloring_book_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }
}
