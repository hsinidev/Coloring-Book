package com.example.coloringbook.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_palettes")
data class UserPaletteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val colors: List<Int>
)
