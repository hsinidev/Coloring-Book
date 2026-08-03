# Coloring Book — Skills & Architecture Guide

## Category: Art / Entertainment / Relaxation

## Description
A beautiful digital coloring book for adults and kids with hundreds of intricate designs, infinite color palette, and calming background music. Mindful coloring on your phone.

## Core Architecture
```
data/
  file/        — File I/O, format parsing, engine wrappers
  database/    — Room entities, DAOs (history, bookmarks, playlists)
  repository/  — Repository pattern implementations
domain/
  model/       — Clean domain models
  usecase/     — Business logic (open file, bookmark, convert, etc.)
presentation/
  ui/screens/  — Compose screens (Home, Viewer/Player, Settings)
  viewmodel/   — StateFlow ViewModels
service/       — ForegroundService for background media/conversion
```

## Key Technical Skills

### File Access (Storage Access Framework)
```kotlin
val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenDocument()
) { uri ->
    uri?.let { viewModel.openFile(it) }
}
// Trigger: launcher.launch(arrayOf("application/pdf", "application/*"))
```

### Foreground Service for Background Processing
```kotlin
class ProcessingService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = buildProgressNotification("Processing...")
        startForeground(NOTIF_ID, notification)
        // Do heavy work in coroutine scope
        serviceScope.launch { doWork() }
        return START_NOT_STICKY
    }
}
```

### Gesture Controls for Media/Document
```kotlin
Modifier.pointerInput(Unit) {
    detectTransformGestures { _, pan, zoom, _ ->
        scale = (scale * zoom).coerceIn(1f, 5f)
        offset += pan
    }
}
```

## Play Store Checklist
- [ ] App icon 512x512 PNG
- [ ] Feature graphic 1024x500 PNG  
- [ ] 4+ screenshots (file browser, main viewer/player, settings, night mode)
- [ ] Privacy Policy (required for storage permission)
- [ ] Content Rating: Everyone
- [ ] Target SDK 34+
- [ ] Release AAB signed with upload keystore

## Unique Value Proposition
Best-in-class zoom precision + time-lapse recording of your coloring session

## Revenue Model
- Free tier with AdMob banner/interstitial ads
- Pro subscription via Google Play Billing: Free 20 pages + $4.99/month Pro (500+ pages)
- Offer 7-day free trial on Pro to maximize conversion
