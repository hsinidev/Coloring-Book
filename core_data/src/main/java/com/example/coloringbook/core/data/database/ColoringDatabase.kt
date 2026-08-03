package com.example.coloringbook.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.coloringbook.core.data.database.dao.DrawingStateDao
import com.example.coloringbook.core.data.database.dao.UserPaletteDao
import com.example.coloringbook.core.data.database.entities.DrawingStateEntity
import com.example.coloringbook.core.data.database.entities.UserPaletteEntity

@Database(
    entities = [DrawingStateEntity::class, UserPaletteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class ColoringDatabase : RoomDatabase() {
    abstract fun drawingStateDao(): DrawingStateDao
    abstract fun userPaletteDao(): UserPaletteDao
}
