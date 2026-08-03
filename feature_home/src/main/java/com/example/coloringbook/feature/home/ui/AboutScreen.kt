package com.example.coloringbook.feature.home.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coloringbook.feature.home.R

// ─────────────────────────────────────────────────────────────────────────────
// Settings & Developer Profile Screen
// Controls haptic, sound preferences, and Room DB coloring reset.
// Displays Developer Profile: Hsini Mohamed · contact@hsini.dev
// ─────────────────────────────────────────────────────────────────────────────

private val DarkCardBg   = Color(0xFF1A2720)   // Deep forest dark green
private val CardBorder   = Color(0xFF2E4038)
private val GoldTitle    = Color(0xFFC5A880)   // Imperial gold
private val TextPrimary  = Color(0xFFF0EDE8)
private val TextSecondary = Color(0xFF9E9E9E)
private val ButtonBg     = Color(0xFF243029)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onNavigateBack: () -> Unit,
    onClearAllData: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("app_settings", Context.MODE_PRIVATE) }
    
    var hapticEnabled by remember { mutableStateOf(sharedPrefs.getBoolean("haptic_feedback_enabled", true)) }
    var soundEnabled by remember { mutableStateOf(sharedPrefs.getBoolean("sound_effects_enabled", true)) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }

    val developerName  = stringResource(R.string.dev_developer_name)
    val email          = stringResource(R.string.dev_contact_email)
    val websiteUrl     = stringResource(R.string.dev_website)
    val githubUrl      = stringResource(R.string.dev_github_url)
    val linkedinUrl    = stringResource(R.string.dev_linkedin_url)

    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            title = { Text("Reset Progress?", fontWeight = FontWeight.Bold) },
            text = { Text("This will permanently clear all your colored drawings. This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearAllData()
                        showResetConfirmDialog = false
                    }
                ) {
                    Text("Reset", color = Color(0xFFC62828), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings & Info") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF0D1310), Color(0xFF111A15))
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ── App Preferences Settings ──────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCardBg),
                border = BorderStroke(1.dp, CardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "App Settings",
                        color = GoldTitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Haptic Feedback Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Haptic Feedback", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("Vibrate slightly on tap actions", color = TextSecondary, fontSize = 12.sp)
                        }
                        Switch(
                            checked = hapticEnabled,
                            onCheckedChange = { checked ->
                                hapticEnabled = checked
                                sharedPrefs.edit().putBoolean("haptic_feedback_enabled", checked).apply()
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldTitle,
                                checkedTrackColor = CardBorder,
                                uncheckedThumbColor = TextSecondary,
                                uncheckedTrackColor = ButtonBg
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sound Effects Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Sound Effects (Sfx)", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("Play sounds during interactions", color = TextSecondary, fontSize = 12.sp)
                        }
                        Switch(
                            checked = soundEnabled,
                            onCheckedChange = { checked ->
                                soundEnabled = checked
                                sharedPrefs.edit().putBoolean("sound_effects_enabled", checked).apply()
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldTitle,
                                checkedTrackColor = CardBorder,
                                uncheckedThumbColor = TextSecondary,
                                uncheckedTrackColor = ButtonBg
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(color = CardBorder, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Reset Coloring Progress
                    Button(
                        onClick = { showResetConfirmDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Reset All Coloring Progress", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Developer Profile Card ────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCardBg),
                border = BorderStroke(1.dp, CardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Developer Profile",
                        color = GoldTitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Name row with avatar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = developerName,
                                color = TextPrimary,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = email,
                                color = TextSecondary,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        DevAvatar()
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Link buttons row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // LinkedIn
                        DevLinkButton(
                            label = "LinkedIn",
                            icon = Icons.Default.Person,
                            modifier = Modifier.weight(1f),
                            onClick = { openUrl(linkedinUrl) }
                        )
                        // Website
                        DevLinkButton(
                            label = "Website",
                            icon = Icons.Default.Language,
                            modifier = Modifier.weight(1f),
                            onClick = { openUrl(websiteUrl) }
                        )
                        // GitHub
                        DevLinkButton(
                            label = "GitHub",
                            icon = null,
                            codeLabel = "<>",
                            modifier = Modifier.weight(1f),
                            onClick = { openUrl(githubUrl) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── App info ─────────────────────────────────────────────────
            Text(
                text = "Coloring Pro",
                color = GoldTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Unleash creativity. Reduce stress. Master the canvas.",
                color = TextSecondary,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Quick links
            OutlinedButton(
                onClick = { openUrl("mailto:$email") },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldTitle),
                border = BorderStroke(1.dp, GoldTitle.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Contact Developer")
            }
        }
    }
}

@Composable
private fun DevAvatar() {
    androidx.compose.foundation.Image(
        painter = androidx.compose.ui.res.painterResource(id = R.drawable.hsini),
        contentDescription = "Developer Photo",
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .border(2.dp, GoldTitle, CircleShape),
        contentScale = androidx.compose.ui.layout.ContentScale.Crop
    )
}

@Composable
private fun DevLinkButton(
    label: String,
    icon: ImageVector?,
    modifier: Modifier = Modifier,
    codeLabel: String? = null,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(50.dp),
        color = ButtonBg,
        border = BorderStroke(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = TextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
            } else if (codeLabel != null) {
                Text(
                    text = codeLabel,
                    color = TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = label,
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
