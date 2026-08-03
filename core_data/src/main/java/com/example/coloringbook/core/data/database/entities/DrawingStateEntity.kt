package com.example.coloringbook.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.coloringbook.core.data.model.ColorFill

@Entity(tableName = "drawing_states")
data class DrawingStateEntity(
    @PrimaryKey val templateId: String,
    val coloredPaths: Map<String, ColorFill>,
    val lastUpdated: Long,
    val isCompleted: Boolean
)
