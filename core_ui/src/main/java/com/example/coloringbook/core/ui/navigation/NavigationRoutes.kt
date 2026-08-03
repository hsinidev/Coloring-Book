package com.example.coloringbook.core.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    object Home : Screen
    
    @Serializable
    data class Canvas(
        val templateId: String,
        val title: String,
        val category: String,
        val isPro: Boolean
    ) : Screen
    
    @Serializable
    object Paywall : Screen
}
