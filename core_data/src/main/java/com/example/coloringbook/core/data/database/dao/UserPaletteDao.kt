package com.example.coloringbook.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coloringbook.core.data.database.entities.UserPaletteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPaletteDao {
    @Query("SELECT * FROM user_palettes")
    fun getAllPalettesFlow(): Flow<List<UserPaletteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePalette(palette: UserPaletteEntity)

    @Query("DELETE FROM user_palettes WHERE id = :id")
    suspend fun deletePalette(id: Int)
}
