package com.example.coloringbook.feature.canvas.viewmodel

import androidx.compose.ui.geometry.Offset
import com.example.coloringbook.core.data.model.ColorFill
import com.example.coloringbook.core.data.model.TextureType
import com.example.coloringbook.feature.canvas.model.DrawingTool

data class CanvasState(
    val templateId: String = "",
    val templateTitle: String = "",
    val templateCategory: String = "",
    val zoom: Float = 1.0f,
    val offset: Offset = Offset.Zero,
    val coloredPaths: Map<String, ColorFill> = emptyMap(),
    val selectedColor: Int = 0xFFFF4081.toInt(), // Vibrant Accent Pink
    val secondColor: Int = 0xFF536DFE.toInt(), // Vibrant Accent Indigo (for gradients)
    val activeTool: DrawingTool = DrawingTool.FILL,
    val selectedTexture: TextureType = TextureType.GLITTER,
    val isPro: Boolean = false,
    val undoStackSize: Int = 0,
    val redoStackSize: Int = 0,
    val isLoading: Boolean = true,
    val isCompleted: Boolean = false
)

sealed interface CanvasIntent {
    data class SelectColor(val color: Int) : CanvasIntent
    data class SelectSecondColor(val color: Int) : CanvasIntent
    data class FillPath(val pathId: String) : CanvasIntent
    data class ZoomPan(val scale: Float, val offset: Offset) : CanvasIntent
    object Undo : CanvasIntent
    object Redo : CanvasIntent
    data class ChangeTool(val tool: DrawingTool) : CanvasIntent
    data class SelectTexture(val texture: TextureType) : CanvasIntent
    object ResetZoomPan : CanvasIntent
    object TogglePro : CanvasIntent // Simulates buying Pro
    object ClearCanvas : CanvasIntent
    object SaveProgress : CanvasIntent
}
