package com.example.coloringbook.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coloringbook.core.data.model.CatalogItem
import com.example.coloringbook.core.data.model.ColorFill
import com.example.coloringbook.feature.home.viewmodel.CatalogViewModel
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import com.example.coloringbook.core.ui.components.CanvasPathData
import com.example.coloringbook.core.ui.components.VectorCanvas
import com.example.coloringbook.feature.canvas.parser.VectorPathParser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    viewModel: CatalogViewModel,
    onNavigateToCanvas: (String, String, String, Boolean) -> Unit,
    onNavigateToPaywall: () -> Unit,
    onShowAmbientMixer: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    val parsedDraftPaths = remember { mutableStateMapOf<String, List<CanvasPathData>>() }

    // Load templates in background for drafts if needed
    LaunchedEffect(state.draftStates) {
        state.draftStates.keys.forEach { templateId ->
            if (!parsedDraftPaths.containsKey(templateId)) {
                try {
                    val stream = context.assets.open("templates/$templateId.xml")
                    val paths = VectorPathParser.parse(stream)
                    parsedDraftPaths[templateId] = paths
                } catch (e: Exception) {
                    // Try mandala fallback
                    try {
                        val stream = context.assets.open("templates/mandala_lion.xml")
                        val paths = VectorPathParser.parse(stream)
                        parsedDraftPaths[templateId] = paths
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            text = "ChromaMind",
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                        Text(
                            text = "Unleash creativity. Reduce stress.",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onShowAmbientMixer) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Ambient Sounds"
                        )
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Stats shelf
            if (state.completedCount > 0 || state.inProgressCount > 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${state.completedCount}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Completed",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${state.inProgressCount}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = "In Progress",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            // Categories Selector Tabs
            if (state.categories.isNotEmpty()) {
                ScrollableTabRow(
                    selectedTabIndex = state.categories.indexOf(state.selectedCategory).coerceAtLeast(0),
                    edgePadding = 16.dp,
                    divider = {},
                    containerColor = Color.Transparent
                ) {
                    state.categories.forEachIndexed { _, category ->
                        Tab(
                            selected = state.selectedCategory == category,
                            onClick = { viewModel.selectCategory(category) },
                            text = {
                                Text(
                                    text = category,
                                    fontWeight = if (state.selectedCategory == category) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))

            // Template Grid
            if (state.items.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(state.items) { item ->
                        TemplateCard(
                            item = item,
                            viewModel = viewModel,
                            onClick = {
                                if (item.isPro) {
                                    onNavigateToPaywall()
                                } else {
                                    onNavigateToCanvas(item.id, item.title, item.category, item.isPro)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun getPreColoredPaths(paths: List<CanvasPathData>, seed: String): Map<String, ColorFill> {
    val map = mutableMapOf<String, ColorFill>()
    
    // Curated harmonious color palette
    val colorPalette = listOf(
        0xFFFF8A80.toInt(), // Light Red
        0xFFFF80AB.toInt(), // Pink
        0xFFEA80FC.toInt(), // Light Purple
        0xFFB388FF.toInt(), // Lavender
        0xFF8C9EFF.toInt(), // Light Blue-Indigo
        0xFF82B1FF.toInt(), // Bright Blue
        0xFF80D8FF.toInt(), // Sky Blue
        0xFF84FFFF.toInt(), // Teal/Cyan
        0xFFA7FFEB.toInt(), // Mint Green
        0xFFB9F6CA.toInt(), // Lime Green
        0xFFCCFF90.toInt(), // Light Green
        0xFFFFE57F.toInt(), // Peach/Gold
        0xFFFFD180.toInt(), // Light Orange
        0xFFFF9E80.toInt()  // Coral
    )
    
    paths.forEach { pathData ->
        if (pathData.id == "bg") {
            map[pathData.id] = ColorFill.Solid(0xFFFAFAFA.toInt())
            return@forEach
        }
        val hash = (pathData.id + seed).hashCode()
        val index = Math.abs(hash) % colorPalette.size
        map[pathData.id] = ColorFill.Solid(colorPalette[index])
    }
    return map
}

@Composable
fun TemplateCard(
    item: CatalogItem,
    viewModel: CatalogViewModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var paths by remember(item.id) { mutableStateOf<List<CanvasPathData>?>(null) }
    var coloredPaths by remember(item.id) { mutableStateOf<Map<String, ColorFill>>(emptyMap()) }

    LaunchedEffect(item.id) {
        kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val stream = context.assets.open("templates/${item.id}.xml")
                val parsed = VectorPathParser.parse(stream)
                paths = parsed
                
                // Fetch saved progress
                val saved = viewModel.getDrawingState(item.id)
                if (saved != null && saved.coloredPaths.isNotEmpty()) {
                    coloredPaths = saved.coloredPaths
                } else {
                    coloredPaths = getPreColoredPaths(parsed, item.id)
                }
            } catch (e: Exception) {
                try {
                    val stream = context.assets.open("templates/nano_banana.xml")
                    val parsed = VectorPathParser.parse(stream)
                    paths = parsed
                    coloredPaths = getPreColoredPaths(parsed, item.id)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    val gradientColors = when (item.difficulty) {
        "Easy" -> listOf(Color(0xFF81C784), Color(0xFF4CAF50))
        "Medium" -> listOf(Color(0xFFFFB74D), Color(0xFFFF9800))
        else -> listOf(Color(0xFFE57373), Color(0xFFF44336))
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                            )
                        )
                    )
            ) {
                // Vector paths outline preview
                paths?.let { list ->
                    VectorCanvas(
                        paths = list,
                        coloredPaths = coloredPaths,
                        viewBoxWidth = 400f,
                        viewBoxHeight = 400f,
                        outlineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        defaultFillColor = Color.Transparent,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                            .padding(bottom = 36.dp) // Leave space for title/difficulty tags at bottom
                    )
                }
                // Featured Tag
                if (item.isDailyFeatured) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(Color(0xFFFFD54F), Color(0xFFFFB300))
                                ),
                                shape = RoundedCornerShape(bottomEnd = 12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Featured",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "Daily Challenge",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
                
                // Locked Pro lock overlay
                if (item.isPro) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.15f))
                    ) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFFFD700).copy(alpha = 0.9f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Pro Required",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                // Title and info
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                            )
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = item.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.horizontalGradient(gradientColors),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = item.difficulty,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        if (item.isPro) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Pro Premium",
                                color = Color(0xFFFFD700),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
