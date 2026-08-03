package com.example.coloringbook.feature.canvas.util

import com.example.coloringbook.core.ui.components.CanvasPathData

object CanvasHitTester {

    fun findPathAtPoint(
        paths: List<CanvasPathData>,
        tapX: Float,
        tapY: Float,
        canvasWidth: Float,
        canvasHeight: Float,
        viewBoxWidth: Float,
        viewBoxHeight: Float,
        zoom: Float,
        offsetX: Float,
        offsetY: Float
    ): String? {
        if (paths.isEmpty() || viewBoxWidth <= 0f || viewBoxHeight <= 0f) return null

        val fitScaleX = canvasWidth / viewBoxWidth
        val fitScaleY = canvasHeight / viewBoxHeight
        val baseScale = minOf(fitScaleX, fitScaleY)
        
        val boundsWidth = viewBoxWidth * baseScale
        val boundsHeight = viewBoxHeight * baseScale
        val centeringX = (canvasWidth - boundsWidth) / 2f
        val centeringY = (canvasHeight - boundsHeight) / 2f

        val scaleFactor = baseScale * zoom
        val templateX = (tapX - offsetX - centeringX) / scaleFactor
        val templateY = (tapY - offsetY - centeringY) / scaleFactor

        // Topmost path first (reverse order)
        for (i in paths.indices.reversed()) {
            val pathData = paths[i]
            if (pathContainsPoint(pathData.path, templateX, templateY)) {
                return pathData.id
            }
        }
        return null
    }

    private fun pathContainsPoint(path: android.graphics.Path, x: Float, y: Float): Boolean {
        val rectF = android.graphics.RectF()
        path.computeBounds(rectF, true)
        
        // Fast bounds check
        if (!rectF.contains(x, y)) return false

        // Scale path and point by 100f to enable sub-pixel precision in Region check
        val scaledPath = android.graphics.Path()
        val matrix = android.graphics.Matrix()
        matrix.setScale(100f, 100f)
        path.transform(matrix, scaledPath)

        val scaledRectF = android.graphics.RectF()
        scaledPath.computeBounds(scaledRectF, true)

        val region = android.graphics.Region()
        val clipRegion = android.graphics.Region(
            (scaledRectF.left - 10).toInt(),
            (scaledRectF.top - 10).toInt(),
            (scaledRectF.right + 10).toInt(),
            (scaledRectF.bottom + 10).toInt()
        )
        region.setPath(scaledPath, clipRegion)
        return region.contains((x * 100f).toInt(), (y * 100f).toInt())
    }
}
