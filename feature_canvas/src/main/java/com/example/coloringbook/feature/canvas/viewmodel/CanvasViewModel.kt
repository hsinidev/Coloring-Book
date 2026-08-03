package com.example.coloringbook.feature.canvas.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coloringbook.core.data.database.ColoringDatabase
import com.example.coloringbook.core.data.database.entities.DrawingStateEntity
import com.example.coloringbook.core.ui.components.CanvasPathData
import com.example.coloringbook.core.data.model.ColorFill
import com.example.coloringbook.feature.canvas.model.DrawingTool
import com.example.coloringbook.feature.canvas.parser.VectorPathParser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CanvasViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: ColoringDatabase
) : ViewModel() {

    private val _state = MutableStateFlow(CanvasState())
    val state: StateFlow<CanvasState> = _state.asStateFlow()

    private val historyManager = CanvasHistoryManager()
    
    // Cached vector paths for hit testing
    var templatePaths: List<CanvasPathData> = emptyList()
        private set

    fun loadTemplate(templateId: String, title: String, category: String, isPro: Boolean) {
        _state.update { it.copy(isLoading = true, templateId = templateId, templateTitle = title, templateCategory = category, isPro = isPro) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Parse vector paths from assets, fallback to nano_banana if not found
                val inputStream = try {
                    context.assets.open("templates/$templateId.xml")
                } catch (e: java.io.FileNotFoundException) {
                    context.assets.open("templates/nano_banana.xml")
                }
                templatePaths = VectorPathParser.parse(inputStream)
                
                // Load saved progress from database
                val savedState = database.drawingStateDao().getDrawingState(templateId)
                val initialColoredPaths = savedState?.coloredPaths ?: emptyMap()
                
                // Reset history stack
                historyManager.clear()
                
                _state.update {
                    it.copy(
                        isLoading = false,
                        coloredPaths = initialColoredPaths,
                        undoStackSize = 0,
                        redoStackSize = 0,
                        isCompleted = savedState?.isCompleted ?: false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun handleIntent(intent: CanvasIntent) {
        when (intent) {
            is CanvasIntent.SelectColor -> {
                _state.update { it.copy(selectedColor = intent.color) }
            }
            is CanvasIntent.SelectSecondColor -> {
                _state.update { it.copy(secondColor = intent.color) }
            }
            is CanvasIntent.ChangeTool -> {
                _state.update { it.copy(activeTool = intent.tool) }
            }
            is CanvasIntent.SelectTexture -> {
                _state.update { it.copy(selectedTexture = intent.texture) }
            }
            is CanvasIntent.ZoomPan -> {
                _state.update { it.copy(zoom = intent.scale, offset = intent.offset) }
            }
            CanvasIntent.ResetZoomPan -> {
                _state.update { it.copy(zoom = 1.0f, offset = androidx.compose.ui.geometry.Offset.Zero) }
            }
            is CanvasIntent.FillPath -> {
                if (_state.value.activeTool == DrawingTool.EYEDROPPER) {
                    val pathId = intent.pathId
                    val colorFill = _state.value.coloredPaths[pathId]
                    val colorInt = when (colorFill) {
                        is ColorFill.Solid -> colorFill.color
                        is ColorFill.LinearGradient -> colorFill.colors.firstOrNull() ?: 0xFFFF4081.toInt()
                        is ColorFill.RadialGradient -> colorFill.colors.firstOrNull() ?: 0xFFFF4081.toInt()
                        is ColorFill.Texture -> colorFill.baseColor
                        null -> {
                            val pathData = templatePaths.find { it.id == pathId }
                            pathData?.originalFillColor ?: 0xFFFFFFFF.toInt()
                        }
                    }
                    _state.update {
                        it.copy(
                            selectedColor = colorInt,
                            activeTool = DrawingTool.FILL
                        )
                    }
                } else {
                    performFillPath(intent.pathId)
                }
            }
            CanvasIntent.Undo -> {
                val previousMap = historyManager.undo(_state.value.coloredPaths)
                if (previousMap != null) {
                    _state.update {
                        it.copy(
                            coloredPaths = previousMap,
                            undoStackSize = historyManager.getUndoSize(),
                            redoStackSize = historyManager.getRedoSize()
                        )
                    }
                    saveProgressToDb(previousMap)
                }
            }
            CanvasIntent.Redo -> {
                val nextMap = historyManager.redo(_state.value.coloredPaths)
                if (nextMap != null) {
                    _state.update {
                        it.copy(
                            coloredPaths = nextMap,
                            undoStackSize = historyManager.getUndoSize(),
                            redoStackSize = historyManager.getRedoSize()
                        )
                    }
                    saveProgressToDb(nextMap)
                }
            }
            CanvasIntent.ClearCanvas -> {
                val currentMap = _state.value.coloredPaths
                if (currentMap.isNotEmpty()) {
                    historyManager.pushState(currentMap)
                    val emptyMap = emptyMap<String, ColorFill>()
                    _state.update {
                        it.copy(
                            coloredPaths = emptyMap,
                            undoStackSize = historyManager.getUndoSize(),
                            redoStackSize = 0
                        )
                    }
                    saveProgressToDb(emptyMap)
                }
            }
            CanvasIntent.TogglePro -> {
                _state.update { it.copy(isPro = !it.isPro) }
            }
            CanvasIntent.SaveProgress -> {
                saveProgressToDb(_state.value.coloredPaths)
            }
        }
    }

    private fun performFillPath(pathId: String) {
        val currentStateMap = _state.value.coloredPaths
        
        // Build the appropriate ColorFill based on active tool
        val newFill = when (_state.value.activeTool) {
            DrawingTool.FILL -> {
                ColorFill.Solid(_state.value.selectedColor)
            }
            DrawingTool.GRADIENT_LINEAR -> {
                ColorFill.LinearGradient(
                    colors = listOf(_state.value.selectedColor, _state.value.secondColor),
                    angle = 45f
                )
            }
            DrawingTool.GRADIENT_RADIAL -> {
                ColorFill.RadialGradient(
                    colors = listOf(_state.value.selectedColor, _state.value.secondColor)
                )
            }
            DrawingTool.TEXTURE -> {
                ColorFill.Texture(
                    type = _state.value.selectedTexture,
                    baseColor = _state.value.selectedColor
                )
            }
            DrawingTool.EYEDROPPER -> {
                // Handled directly via hit-test or long-press on screen
                return
            }
        }

        // Push current state to undo history before making changes
        historyManager.pushState(currentStateMap)

        val updatedMap = currentStateMap.toMutableMap().apply {
            put(pathId, newFill)
        }

        // Check if all paths are colored
        val isCompleted = templatePaths.isNotEmpty() && templatePaths.all { updatedMap.containsKey(it.id) }

        _state.update {
            it.copy(
                coloredPaths = updatedMap,
                undoStackSize = historyManager.getUndoSize(),
                redoStackSize = 0,
                isCompleted = isCompleted
            )
        }

        saveProgressToDb(updatedMap, isCompleted)
    }

    private fun saveProgressToDb(coloredPaths: Map<String, ColorFill>, isCompleted: Boolean = false) {
        val templateId = _state.value.templateId
        if (templateId.isEmpty()) return
        
        viewModelScope.launch(Dispatchers.IO) {
            database.drawingStateDao().saveDrawingState(
                DrawingStateEntity(
                    templateId = templateId,
                    coloredPaths = coloredPaths,
                    lastUpdated = System.currentTimeMillis(),
                    isCompleted = isCompleted
                )
            )
        }
    }
}
