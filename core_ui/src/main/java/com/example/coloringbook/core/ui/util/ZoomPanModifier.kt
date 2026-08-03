package com.example.coloringbook.core.ui.util

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.sqrt

fun Modifier.zoomPanGestures(
    zoom: Float,
    offset: Offset,
    canvasWidth: Float,
    canvasHeight: Float,
    viewBoxWidth: Float = 400f,
    viewBoxHeight: Float = 400f,
    minZoom: Float = 0.5f,
    maxZoom: Float = 8.0f,
    onTransform: (zoom: Float, offset: Offset) -> Unit,
    onTap: (Offset) -> Unit = {},
    onDoubleTap: () -> Unit = {},
    onLongPress: (Offset) -> Unit = {}
): Modifier = this.pointerInput(zoom, offset, canvasWidth, canvasHeight) {
    coroutineScope {
        val scope = this
        // Combined gesture detector
        launch {
            detectTapGestures(
                onTap = { position -> onTap(position) },
                onDoubleTap = { onDoubleTap() },
                onLongPress = { position -> onLongPress(position) }
            )
        }
        
        launch {
            awaitEachGesture {
                var lastCentroid = Offset.Zero
                var velX = 0f
                var velY = 0f
                var lastTime = System.currentTimeMillis()

                // Wait for first touch
                awaitFirstDown()
                
                while (true) {
                    val event = awaitPointerEvent()
                    val currentTime = System.currentTimeMillis()
                    val deltaTime = maxOf(1L, currentTime - lastTime)
                    lastTime = currentTime
                    
                    val changes = event.changes
                    val count = changes.filter { it.pressed }.size
                    
                    if (count >= 2) {
                        val zoomChange = event.calculateZoom()
                        val panChange = event.calculatePan()
                        val centroid = event.calculateCentroid()
                        
                        if (zoomChange != 1f || panChange != Offset.Zero) {
                            val newZoom = (zoom * zoomChange).coerceIn(minZoom, maxZoom)
                            val actualZoomChange = newZoom / zoom
                            
                            val fitScaleX = canvasWidth / viewBoxWidth
                            val fitScaleY = canvasHeight / viewBoxHeight
                            val baseScale = if (canvasWidth > 0 && canvasHeight > 0) minOf(fitScaleX, fitScaleY) else 1f
                            val boundsWidth = viewBoxWidth * baseScale
                            val boundsHeight = viewBoxHeight * baseScale
                            val centeringX = (canvasWidth - boundsWidth) / 2f
                            val centeringY = (canvasHeight - boundsHeight) / 2f
                            val centering = Offset(centeringX, centeringY)

                            val newOffset = if (canvasWidth > 0 && canvasHeight > 0) {
                                centroid - centering - (centroid - centering - offset) * actualZoomChange + panChange
                            } else {
                                offset + panChange
                            }
                            onTransform(newZoom, newOffset)
                            
                            if (lastCentroid != Offset.Zero) {
                                val dx = centroid.x - lastCentroid.x
                                val dy = centroid.y - lastCentroid.y
                                velX = (dx / deltaTime) * 16f
                                velY = (dy / deltaTime) * 16f
                            }
                            lastCentroid = centroid
                        }
                    } else {
                        lastCentroid = Offset.Zero
                    }
                    
                    val allReleased = changes.all { !it.pressed }
                    if (allReleased) {
                        val speed = sqrt(velX * velX + velY * velY)
                        if (speed > 2f) {
                            scope.launch {
                                var currentVelX = velX * 0.8f
                                var currentVelY = velY * 0.8f
                                var currentOffset = offset
                                while (abs(currentVelX) > 0.2f || abs(currentVelY) > 0.2f) {
                                    currentOffset = Offset(
                                        (currentOffset.x + currentVelX).coerceIn(-5000f, 5000f),
                                        (currentOffset.y + currentVelY).coerceIn(-5000f, 5000f)
                                    )
                                    onTransform(zoom, currentOffset)
                                    currentVelX *= 0.90f
                                    currentVelY *= 0.90f
                                    delay(16)
                                }
                            }
                        }
                        break
                    }
                }
            }
        }
    }
}
