package com.example.coloringbook.core.ui.components

import android.graphics.Path as AndroidPath
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalContext
import com.example.coloringbook.core.data.model.ColorFill
import com.example.coloringbook.core.ui.util.BrushProvider

data class CanvasPathData(
    val id: String,
    val path: AndroidPath,
    val originalFillColor: Int? = null,
    val strokeColor: Int? = null,
    val strokeWidth: Float? = null
)

@Composable
fun VectorCanvas(
    paths: List<CanvasPathData>,
    coloredPaths: Map<String, ColorFill>,
    viewBoxWidth: Float,
    viewBoxHeight: Float,
    modifier: Modifier = Modifier,
    scale: Float = 1f,
    offsetX: Float = 0f,
    offsetY: Float = 0f,
    outlineColor: Color = Color.Black,
    defaultFillColor: Color = Color.White
) {
    val context = LocalContext.current
    
    // Pre-resolve brushes
    val brushes = remember(coloredPaths) {
        coloredPaths.mapValues { (_, fill) ->
            BrushProvider.createBrush(fill, context)
        }
    }
    
    val defaultBrush = remember(defaultFillColor) {
        Brush.linearGradient(listOf(defaultFillColor, defaultFillColor))
    }

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        
        // Compute base scaling to fit the viewBox inside the canvas bounds
        val fitScaleX = canvasWidth / viewBoxWidth
        val fitScaleY = canvasHeight / viewBoxHeight
        val baseScale = minOf(fitScaleX, fitScaleY)
        
        // Compute centering offsets
        val boundsWidth = viewBoxWidth * baseScale
        val boundsHeight = viewBoxHeight * baseScale
        val centeringX = (canvasWidth - boundsWidth) / 2f
        val centeringY = (canvasHeight - boundsHeight) / 2f

        withTransform({
            // Apply pan offset
            translate(offsetX, offsetY)
            // Centering translation
            translate(centeringX, centeringY)
            // Apply zoom scale around the center (or pivot)
            scale(baseScale * scale, baseScale * scale, pivot = androidx.compose.ui.geometry.Offset.Zero)
        }) {
            // 1. Draw path fills
            paths.forEach { pathData ->
                val composePath = pathData.path.asComposePath()
                val brush = brushes[pathData.id] ?: defaultBrush
                drawPath(
                    path = composePath,
                    brush = brush
                )
            }
            
            // 2. Draw path strokes (outlines)
            paths.forEach { pathData ->
                val composePath = pathData.path.asComposePath()
                val width = pathData.strokeWidth ?: 1f
                val color = pathData.strokeColor?.let { Color(it) } ?: outlineColor
                drawPath(
                    path = composePath,
                    color = color,
                    style = Stroke(width = width)
                )
            }
        }
    }
}
