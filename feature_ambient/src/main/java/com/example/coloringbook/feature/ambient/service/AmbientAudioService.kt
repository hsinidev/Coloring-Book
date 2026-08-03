package com.example.coloringbook.feature.ambient.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class AmbientAudioService : Service() {

    private val binder = AmbientBinder()
    
    // Soundscape tracks
    private val tracks = listOf(
        SoundTrack("rain", "Rain", "https://assets.mixkit.co/active_storage/sfx/2433/2433-84.wav"),
        SoundTrack("lofi", "Lo-Fi", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"),
        SoundTrack("bowls", "Singing Bowls", "https://assets.mixkit.co/active_storage/sfx/1657/1657-84.wav"),
        SoundTrack("breeze", "Forest Breeze", "https://assets.mixkit.co/active_storage/sfx/1243/1243-84.wav"),
        SoundTrack("noise", "Pink Noise", "https://assets.mixkit.co/active_storage/sfx/2568/2568-84.wav")
    )
    
    private val players = mutableMapOf<String, ExoPlayer>()
    private val volumes = mutableMapOf<String, Float>()
    private var isPlaying = false

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        
        // Initialize ExoPlayers for each track
        tracks.forEach { track ->
            val player = ExoPlayer.Builder(this).build().apply {
                val mediaItem = MediaItem.fromUri(track.url)
                setMediaItem(mediaItem)
                repeatMode = Player.REPEAT_MODE_ALL
                volume = 0.0f // Initial silent volume
                prepare()
            }
            players[track.id] = player
            volumes[track.id] = 0.0f
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID, 
                notification, 
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
        return START_NOT_STICKY
    }

    fun startAll() {
        if (!isPlaying) {
            players.values.forEach { it.play() }
            isPlaying = true
            updateNotification()
        }
    }

    fun stopAll() {
        if (isPlaying) {
            players.values.forEach { it.pause() }
            isPlaying = false
            updateNotification()
        }
    }

    fun setVolume(trackId: String, volume: Float) {
        volumes[trackId] = volume
        players[trackId]?.volume = volume
    }

    fun getVolume(trackId: String): Float = volumes[trackId] ?: 0.0f

    fun isServicePlaying(): Boolean = isPlaying

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        players.values.forEach {
            it.stop()
            it.release()
        }
        players.clear()
    }

    private fun createNotification(): Notification {
        val playPauseText = if (isPlaying) "Pause All" else "Play All"
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("ChromaMind Ambient Soundscapes")
            .setContentText("Relaxing background ambient mixer")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true)
            .build()
    }

    private fun updateNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Ambient Soundscapes",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Background playback for relaxing soundscapes"
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    inner class AmbientBinder : Binder() {
        fun getService(): AmbientAudioService = this@AmbientAudioService
    }

    companion object {
        private const val CHANNEL_ID = "ambient_sounds_channel"
        private const val NOTIFICATION_ID = 1001
    }
}

data class SoundTrack(
    val id: String,
    val name: String,
    val url: String
)
