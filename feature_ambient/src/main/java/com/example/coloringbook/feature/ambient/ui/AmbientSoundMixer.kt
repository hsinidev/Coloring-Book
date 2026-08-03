package com.example.coloringbook.feature.ambient.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coloringbook.feature.ambient.service.AmbientAudioService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmbientSoundMixer(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var service by remember { mutableStateOf<AmbientAudioService?>(null) }
    var isBound by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }

    val volumesState = remember {
        mutableStateMapOf(
            "rain" to 0.0f,
            "lofi" to 0.0f,
            "bowls" to 0.0f,
            "breeze" to 0.0f,
            "noise" to 0.0f
        )
    }

    val serviceConnection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                val ambientBinder = binder as? AmbientAudioService.AmbientBinder
                service = ambientBinder?.getService()
                isBound = true
                service?.let { svc ->
                    isPlaying = svc.isServicePlaying()
                    volumesState["rain"] = svc.getVolume("rain")
                    volumesState["lofi"] = svc.getVolume("lofi")
                    volumesState["bowls"] = svc.getVolume("bowls")
                    volumesState["breeze"] = svc.getVolume("breeze")
                    volumesState["noise"] = svc.getVolume("noise")
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                service = null
                isBound = false
            }
        }
    }

    DisposableEffect(context) {
        val intent = Intent(context, AmbientAudioService::class.java)
        try {
            context.startService(intent)
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        onDispose {
            if (isBound) {
                try {
                    context.unbindService(serviceConnection)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mindfulness Sound Mixer",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        service?.let { svc ->
                            if (isPlaying) {
                                svc.stopAll()
                            } else {
                                svc.startAll()
                            }
                            isPlaying = svc.isServicePlaying()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isPlaying) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = if (isPlaying) "Pause Soundscapes" else "Start Soundscapes")
                }

                HorizontalDivider()

                val soundscapes = listOf(
                    "rain" to "🌧️ Rain",
                    "lofi" to "☕ Lo-Fi Ambient",
                    "bowls" to "🥣 Singing Bowls",
                    "breeze" to "🍃 Forest Breeze",
                    "noise" to "🔊 Pink Noise"
                )

                soundscapes.forEach { (id, label) ->
                    val volume = volumesState[id] ?: 0.0f
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = label, fontWeight = FontWeight.Medium)
                            Text(text = "${(volume * 100).toInt()}%", color = MaterialTheme.colorScheme.primary)
                        }
                        Slider(
                            value = volume,
                            onValueChange = { newValue ->
                                volumesState[id] = newValue
                                service?.setVolume(id, newValue)
                            },
                            valueRange = 0f..1f
                        )
                    }
                }
            }
        },
        confirmButton = {}
    )
}
