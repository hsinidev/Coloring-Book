package com.example.coloringbook.core.data.database

import androidx.room.TypeConverter
import com.example.coloringbook.core.data.model.ColorFill
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object RoomConverters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    @JvmStatic
    fun fromColoredPathsMap(value: Map<String, ColorFill>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    @JvmStatic
    fun toColoredPathsMap(value: String): Map<String, ColorFill> {
        return try {
            json.decodeFromString(value)
        } catch (e: Exception) {
            emptyMap()
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromIntList(value: List<Int>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    @JvmStatic
    fun toIntList(value: String): List<Int> {
        return try {
            json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
