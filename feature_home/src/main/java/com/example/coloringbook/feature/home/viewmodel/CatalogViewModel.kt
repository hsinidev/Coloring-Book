package com.example.coloringbook.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coloringbook.core.data.database.ColoringDatabase
import com.example.coloringbook.core.data.model.CatalogItem
import com.example.coloringbook.core.data.model.CatalogData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class CatalogUiState(
    val items: List<CatalogItem> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "Animals",
    val completedCount: Int = 0,
    val inProgressCount: Int = 0,
    val draftStates: Map<String, Boolean> = emptyMap()
)

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val database: ColoringDatabase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("Animals")

    val uiState: StateFlow<CatalogUiState> = combine(
        _selectedCategory,
        database.drawingStateDao().getAllDrawingStatesFlow()
    ) { category, savedStates ->
        val draftMap = savedStates.associate { it.templateId to it.isCompleted }
        val completedCount = savedStates.count { it.isCompleted }
        val inProgressCount = savedStates.count { !it.isCompleted && it.coloredPaths.isNotEmpty() }
        
        val filteredItems = CatalogData.templates.filter { it.category == category }
        val categories = CatalogData.templates.map { it.category }.distinct()

        CatalogUiState(
            items = filteredItems,
            categories = categories,
            selectedCategory = category,
            completedCount = completedCount,
            inProgressCount = inProgressCount,
            draftStates = draftMap
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CatalogUiState()
    )

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    suspend fun getDrawingState(templateId: String): com.example.coloringbook.core.data.database.entities.DrawingStateEntity? {
        return database.drawingStateDao().getDrawingState(templateId)
    }
}
