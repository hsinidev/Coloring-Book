package com.example.coloringbook.feature.pro.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coloringbook.feature.pro.billing.BillingManager

@Composable
fun PaywallScreen(
    billingManager: BillingManager,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isProUnlocked by billingManager.isProUnlocked.collectAsState()

    if (isProUnlocked) {
        // Automatically close paywall once Pro is unlocked
        onClose()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF311B92), Color(0xFF1A237E)) // Sleek dark indigo/purple
                )
            )
    ) {
        // Close button
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Crown / Gold Star icon
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFFFD700), // Gold
                modifier = Modifier.size(72.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Gold Star",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "ChromaMind Pro Premium",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Unleash the full coloring experience",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Pro Features list
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ProFeatureItem("🔓 Unlock 1000+ vector templates & daily challenges")
                ProFeatureItem("✨ Use custom glitter, metallic, & brush stroke textures")
                ProFeatureItem("🔊 Ad-free ambient soundscapes mixer")
                ProFeatureItem("🖼️ Unlimited high-res PNG & PDF vector printing exports")
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Purchase Button
            Button(
                onClick = {
                    val activity = context.findActivity()
                    if (activity != null) {
                        billingManager.launchBillingFlow(activity)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700), // Gold CTA button
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(28.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "Unlock Pro Premium - $4.99/mo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { billingManager.restorePurchases() }) {
                Text(
                    text = "Restore Purchases",
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ProFeatureItem(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

private fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
