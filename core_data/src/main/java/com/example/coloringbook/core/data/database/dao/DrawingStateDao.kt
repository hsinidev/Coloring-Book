package com.example.coloringbook.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coloringbook.core.data.database.entities.DrawingStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrawingStateDao {
    @Query("SELECT * FROM drawing_states WHERE templateId = :templateId")
    suspend fun getDrawingState(templateId: String): DrawingStateEntity?

    @Query("SELECT * FROM drawing_states WHERE templateId = :templateId")
    fun getDrawingStateFlow(templateId: String): Flow<DrawingStateEntity?>

    @Query("SELECT * FROM drawing_states ORDER BY lastUpdated DESC")
    fun getAllDrawingStatesFlow(): Flow<List<DrawingStateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDrawingState(state: DrawingStateEntity)

    @Query("DELETE FROM drawing_states WHERE templateId = :templateId")
    suspend fun deleteDrawingState(templateId: String)

    @Query("DELETE FROM drawing_states")
    suspend fun deleteAllDrawingStates()
}
