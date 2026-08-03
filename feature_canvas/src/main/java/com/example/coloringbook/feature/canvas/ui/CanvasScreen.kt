package com.example.coloringbook.feature.canvas.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coloringbook.core.data.model.ColorFill
import com.example.coloringbook.core.data.model.TextureType
import com.example.coloringbook.core.ui.components.VectorCanvas
import com.example.coloringbook.core.ui.util.zoomPanGestures
import com.example.coloringbook.feature.canvas.model.DrawingTool
import com.example.coloringbook.feature.canvas.util.CanvasHitTester
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import com.example.coloringbook.feature.canvas.viewmodel.CanvasIntent
import com.example.coloringbook.feature.canvas.viewmodel.CanvasViewModel
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasScreen(
    viewModel: CanvasViewModel,
    onNavigateBack: () -> Unit,
    onShowAmbientMixer: () -> Unit,
    onExportDrawing: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("app_settings", android.content.Context.MODE_PRIVATE) }
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    
    val colorsList = listOf(
        0xFFF44336.toInt(), 0xFFE91E63.toInt(), 0xFF9C27B0.toInt(), 0xFF673AB7.toInt(),
        0xFF3F51B5.toInt(), 0xFF2196F3.toInt(), 0xFF03A9F4.toInt(), 0xFF00BCD4.toInt(),
        0xFF009688.toInt(), 0xFF4CAF50.toInt(), 0xFF8BC34A.toInt(), 0xFFCDDC39.toInt(),
        0xFFFFEB3B.toInt(), 0xFFFFC107.toInt(), 0xFFFF9800.toInt(), 0xFFFF5722.toInt(),
        0xFF795548.toInt(), 0xFF9E9E9E.toInt(), 0xFF607D8B.toInt(), 0xFF000000.toInt(),
        0xFFFFFFFF.toInt()
    )

    val texturesList = listOf(
        TextureType.GLITTER,
        TextureType.METALLIC,
        TextureType.PAPER,
        TextureType.BRUSH_STROKE
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.templateTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // Ambient Music button
                    IconButton(onClick = onShowAmbientMixer) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Ambient Sounds"
                        )
                    }
                    // Export button
                    IconButton(onClick = onExportDrawing) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Export"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Zoom resetting and Undo/Redo tools bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        IconButton(
                            onClick = { viewModel.handleIntent(CanvasIntent.Undo) },
                            enabled = state.undoStackSize > 0
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Undo,
                                contentDescription = "Undo"
                            )
                        }
                        IconButton(
                            onClick = { viewModel.handleIntent(CanvasIntent.Redo) },
                            enabled = state.redoStackSize > 0
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Redo,
                                contentDescription = "Redo"
                            )
                        }
                    }
                    
                    IconButton(
                        onClick = { viewModel.handleIntent(CanvasIntent.ResetZoomPan) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Recenter"
                        )
                    }
                }

                // Vector Canvas Drawing Area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(24.dp))
                        .onSizeChanged { canvasSize = it }
                        .zoomPanGestures(
                            zoom = state.zoom,
                            offset = state.offset,
                            canvasWidth = canvasSize.width.toFloat(),
                            canvasHeight = canvasSize.height.toFloat(),
                            onTransform = { zoom, offset ->
                                viewModel.handleIntent(CanvasIntent.ZoomPan(zoom, offset))
                            },
                            onTap = { position ->
                                val hitPathId = CanvasHitTester.findPathAtPoint(
                                    paths = viewModel.templatePaths,
                                    tapX = position.x,
                                    tapY = position.y,
                                    canvasWidth = canvasSize.width.toFloat(),
                                    canvasHeight = canvasSize.height.toFloat(),
                                    viewBoxWidth = 400f,
                                    viewBoxHeight = 400f,
                                    zoom = state.zoom,
                                    offsetX = state.offset.x,
                                    offsetY = state.offset.y
                                )
                                if (hitPathId != null) {
                                    viewModel.handleIntent(CanvasIntent.FillPath(hitPathId))
                                    if (sharedPrefs.getBoolean("haptic_feedback_enabled", true)) {
                                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                    }
                                }
                            }
                        )
                ) {
                    VectorCanvas(
                        paths = viewModel.templatePaths,
                        coloredPaths = state.coloredPaths,
                        viewBoxWidth = 400f,
                        viewBoxHeight = 400f,
                        scale = state.zoom,
                        offsetX = state.offset.x,
                        offsetY = state.offset.y,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom controls shelf
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Photoshop tools selection scrollable row
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item {
                                FilterChip(
                                    selected = state.activeTool == DrawingTool.FILL,
                                    onClick = { viewModel.handleIntent(CanvasIntent.ChangeTool(DrawingTool.FILL)) },
                                    label = { Text("🪣 Solid Fill") }
                                )
                            }
                            item {
                                FilterChip(
                                    selected = state.activeTool == DrawingTool.GRADIENT_LINEAR,
                                    onClick = { viewModel.handleIntent(CanvasIntent.ChangeTool(DrawingTool.GRADIENT_LINEAR)) },
                                    label = { Text("📐 Linear Gx") }
                                )
                            }
                            item {
                                FilterChip(
                                    selected = state.activeTool == DrawingTool.GRADIENT_RADIAL,
                                    onClick = { viewModel.handleIntent(CanvasIntent.ChangeTool(DrawingTool.GRADIENT_RADIAL)) },
                                    label = { Text("🎯 Radial Gx") }
                                )
                            }
                            item {
                                FilterChip(
                                    selected = state.activeTool == DrawingTool.TEXTURE,
                                    onClick = { viewModel.handleIntent(CanvasIntent.ChangeTool(DrawingTool.TEXTURE)) },
                                    label = { Text("✨ Shaders") }
                                )
                            }
                            item {
                                FilterChip(
                                    selected = state.activeTool == DrawingTool.EYEDROPPER,
                                    onClick = { viewModel.handleIntent(CanvasIntent.ChangeTool(DrawingTool.EYEDROPPER)) },
                                    label = { Text("🧪 Eyedropper") }
                                )
                            }
                            item {
                                FilterChip(
                                    selected = false,
                                    onClick = { viewModel.handleIntent(CanvasIntent.ResetZoomPan) },
                                    label = { Text("🔄 Recenter") }
                                )
                            }
                            item {
                                FilterChip(
                                    selected = false,
                                    onClick = { viewModel.handleIntent(CanvasIntent.ClearCanvas) },
                                    label = { Text("🧹 Reset Canvas") }
                                )
                            }
                        }

                        // Colors list, gradients, shaders, or eyedropper instructions
                        when (state.activeTool) {
                            DrawingTool.FILL -> {
                                Text("Select Color", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(6.dp))
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    contentPadding = PaddingValues(horizontal = 4.dp)
                                ) {
                                    items(colorsList) { colorInt ->
                                        ColorBox(colorInt, state.selectedColor) {
                                            viewModel.handleIntent(CanvasIntent.SelectColor(colorInt))
                                        }
                                    }
                                }
                            }
                            DrawingTool.GRADIENT_LINEAR, DrawingTool.GRADIENT_RADIAL -> {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Column {
                                        Text("Gradient Color 1 (Start)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            contentPadding = PaddingValues(horizontal = 4.dp)
                                        ) {
                                            items(colorsList) { colorInt ->
                                                ColorBox(colorInt, state.selectedColor) {
                                                    viewModel.handleIntent(CanvasIntent.SelectColor(colorInt))
                                                }
                                            }
                                        }
                                    }
                                    Column {
                                        Text("Gradient Color 2 (End)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            contentPadding = PaddingValues(horizontal = 4.dp)
                                        ) {
                                            items(colorsList) { colorInt ->
                                                ColorBox(colorInt, state.secondColor) {
                                                    viewModel.handleIntent(CanvasIntent.SelectSecondColor(colorInt))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            DrawingTool.TEXTURE -> {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Column {
                                        Text("Select Base Color", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            contentPadding = PaddingValues(horizontal = 4.dp)
                                        ) {
                                            items(colorsList) { colorInt ->
                                                ColorBox(colorInt, state.selectedColor) {
                                                    viewModel.handleIntent(CanvasIntent.SelectColor(colorInt))
                                                }
                                            }
                                        }
                                    }
                                    Column {
                                        Text("Select Shader Texture", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                            contentPadding = PaddingValues(horizontal = 4.dp)
                                        ) {
                                            items(texturesList) { textureType ->
                                                FilterChip(
                                                    selected = state.selectedTexture == textureType,
                                                    onClick = {
                                                        viewModel.handleIntent(CanvasIntent.SelectTexture(textureType))
                                                    },
                                                    label = {
                                                        Text(
                                                            text = when (textureType) {
                                                                TextureType.GLITTER -> "✨ Glitter"
                                                                TextureType.METALLIC -> "💿 Metallic"
                                                                TextureType.PAPER -> "📝 Paper"
                                                                TextureType.BRUSH_STROKE -> "🎨 Brush Strokes"
                                                            }
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            DrawingTool.EYEDROPPER -> {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "🧪 Eyedropper active: Tap anywhere on the canvas to pick its color!",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorBox(
    colorInt: Int,
    selectedColor: Int,
    onClick: () -> Unit
) {
    val color = Color(colorInt)
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                BorderStroke(
                    if (selectedColor == colorInt) 3.dp else 1.dp,
                    if (selectedColor == colorInt) MaterialTheme.colorScheme.primary else Color.Black.copy(alpha = 0.2f)
                ),
                CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (selectedColor == colorInt) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = if (colorInt == 0xFFFFFFFF.toInt()) Color.Black else Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
