package com.example.coloringbook

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coloringbook.core.data.database.ColoringDatabase
import com.example.coloringbook.feature.ambient.ui.AmbientSoundMixer
import com.example.coloringbook.feature.canvas.ui.CanvasScreen
import com.example.coloringbook.feature.canvas.util.CanvasExporter
import com.example.coloringbook.feature.canvas.viewmodel.CanvasViewModel
import com.example.coloringbook.feature.home.ui.CatalogScreen
import com.example.coloringbook.feature.home.ui.AboutScreen
import com.example.coloringbook.feature.home.viewmodel.CatalogViewModel
import com.example.coloringbook.feature.pro.billing.BillingManager
import com.example.coloringbook.feature.pro.ui.PaywallScreen
import com.example.coloringbook.ui.theme.ColoringBookTheme
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var billingManager: BillingManager

    @Inject
    lateinit var database: ColoringDatabase

    private val catalogViewModel: CatalogViewModel by viewModels()
    private val canvasViewModel: CanvasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ColoringBookTheme {
                var showAmbientMixer by remember { mutableStateOf(false) }
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            CatalogScreen(
                                viewModel = catalogViewModel,
                                onNavigateToCanvas = { id, title, type, isPro ->
                                    navController.navigate("canvas/$id/$title/$type/$isPro")
                                },
                                onNavigateToPaywall = {
                                    navController.navigate("paywall")
                                },
                                onShowAmbientMixer = {
                                    showAmbientMixer = true
                                },
                                onNavigateToSettings = {
                                    navController.navigate("settings")
                                }
                            )
                        }

                        composable(
                            route = "canvas/{id}/{title}/{type}/{isPro}",
                            arguments = listOf(
                                navArgument("id") { type = NavType.StringType },
                                navArgument("title") { type = NavType.StringType },
                                navArgument("type") { type = NavType.StringType },
                                navArgument("isPro") { type = NavType.BoolType }
                            )
                        ) { backStackEntry ->
                            val templateId = backStackEntry.arguments?.getString("id") ?: ""
                            val title = backStackEntry.arguments?.getString("title") ?: ""
                            val type = backStackEntry.arguments?.getString("type") ?: ""
                            val isPro = backStackEntry.arguments?.getBoolean("isPro") ?: false

                            LaunchedEffect(templateId) {
                                canvasViewModel.loadTemplate(templateId, title, type, isPro)
                            }

                            CanvasScreen(
                                viewModel = canvasViewModel,
                                onNavigateBack = {
                                    navController.popBackStack()
                                },
                                onShowAmbientMixer = {
                                    showAmbientMixer = true
                                },
                                onExportDrawing = {
                                    val bitmap = CanvasExporter.exportToBitmap(
                                        this@MainActivity,
                                        canvasViewModel.templatePaths,
                                        canvasViewModel.state.value.coloredPaths
                                    )
                                    val uri = CanvasExporter.saveBitmapToGallery(
                                        this@MainActivity,
                                        bitmap,
                                        "Drawing_${templateId}"
                                    )
                                    if (uri != null) {
                                        Toast.makeText(this@MainActivity, "Saved to Gallery!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this@MainActivity, "Export failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }

                        composable("paywall") {
                            PaywallScreen(
                                billingManager = billingManager,
                                onClose = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("settings") {
                            AboutScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                },
                                onClearAllData = {
                                    lifecycleScope.launch(Dispatchers.IO) {
                                        database.drawingStateDao().deleteAllDrawingStates()
                                    }
                                }
                            )
                        }
                    }

                    if (showAmbientMixer) {
                        AmbientSoundMixer(
                            onDismiss = { showAmbientMixer = false }
                        )
                    }
                }
            }
        }
    }
}