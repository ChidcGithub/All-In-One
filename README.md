# All in one - Android Native API Showcase

A comprehensive technical demonstration application that systematically showcases Android's core native APIs, system services, and hardware interfaces. Built with Jetpack Compose and Material 3 Expressive.

## Overview

All in one is an educational and reference application designed for Android developers and technical enthusiasts who want to understand the full breadth of Android system capabilities. It provides working demonstrations of nearly every major Android API category in a single, well-organized application.

## Technical Specifications

- **Minimum SDK**: API 36 (Android 16 Baklava)
- **Target SDK**: API 36
- **UI Framework**: 100% Jetpack Compose
- **Design System**: Material 3 Expressive
- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **Build System**: Gradle with Kotlin DSL and Version Catalogs

## Tech Stack

### Core
- Jetpack Compose (UI)
- Material 3 Expressive (Design)
- Compose Navigation (Type-safe navigation with kotlinx.serialization)
- Lifecycle ViewModel (State management)
- Kotlin Coroutines & Flow (Asynchronous programming)

### Data
- Room (SQLite ORM)
- DataStore (Preferences)
- SharedPreferences (Legacy key-value storage)
- KSP (Kotlin Symbol Processing)

### System Integration
- CameraX (Camera operations)
- WorkManager (Background task scheduling)
- SensorManager (Hardware sensors)
- ConnectivityManager (Network monitoring)
- BluetoothManager (BLE operations)
- Location Services (GPS and network location)
- NotificationManager (System notifications)
- AlarmManager (Precise scheduling)

### Permissions
- Accompanist Permissions (Runtime permission requests)

## Project Structure

```
app/src/main/java/com/allinone/
├── MainActivity.kt                    # Edge-to-edge Compose entry point
├── AllInOneApp.kt                     # Application class with DI initialization
├── core/
│   ├── base/                          # Base ViewModel and UI state
│   ├── data/
│   │   ├── database/                  # Room database and DAOs
│   │   └── repository/                # Unified data repository
│   ├── di/                            # Simple dependency injection
│   ├── theme/                         # M3 Expressive theme system
│   │   ├── Color.kt                   # Extended color palette
│   │   ├── Typography.kt              # 30 font styles (15 baseline + 15 emphasis)
│   │   ├── Shapes.kt                  # 35 shape variants
│   │   └── Theme.kt                   # Dynamic color and dark mode
│   └── ui/components/                 # Reusable Compose components
├── navigation/
│   ├── DemoModules.kt                 # Module definitions and routes
│   └── NavHost.kt                     # Centralized navigation host
└── feature/
    ├── home/                          # Home screen with search and filtering
    ├── system/                        # Core framework modules
    │   ├── NavigationScreen.kt        # Activity lifecycle and launch modes
    │   ├── ServicesScreen.kt          # Foreground, background, bound services
    │   ├── BroadcastScreen.kt         # Static and dynamic broadcast receivers
    │   ├── ContentProviderScreen.kt   # Cross-application data sharing
    │   ├── LocationScreen.kt          # GPS, network, and geofencing
    │   ├── NotificationScreen.kt      # Notification channels and types
    │   └── AccessibilityScreen.kt     # Accessibility services
    ├── ui/                            # UI and interaction modules
    │   ├── UILayoutsScreen.kt         # Layout components
    │   ├── M3ExpressiveScreen.kt      # M3 Expressive components
    │   ├── AnimationsScreen.kt        # Spring and transition animations
    │   └── CustomDrawingScreen.kt     # Canvas and shape drawing
    ├── sensors/                       # Hardware sensor demonstrations
    ├── multimedia/                    # Camera, audio, and video
    ├── connectivity/                  # Bluetooth, NFC, Wi-Fi, and network
    ├── storage/                       # Local storage implementations
    ├── background/                    # Background task scheduling
    ├── permissions/                   # Runtime and special permissions
    └── android16/                     # Android 16 exclusive APIs
```

## Feature Modules

### Core Framework

| Module | Demonstrates |
|--------|-------------|
| Activity & Navigation | Activity lifecycle, launch modes (Standard, SingleTop, SingleTask, SingleInstance), intent flags, Compose type-safe navigation |
| Services | Foreground services with notifications, background services, bound services with IBinder |
| BroadcastReceiver | Dynamic registration, static manifest registration, system broadcast monitoring, ordered broadcasts |
| ContentProvider | Custom provider implementation, CRUD operations, system provider queries (Contacts, MediaStore) |

### UI and Interaction

| Module | Demonstrates |
|--------|-------------|
| UI & Layouts | Column, Row, Box, LazyColumn, LazyRow, ScrollView, Tabs, Buttons, Sliders, animated content |
| M3 Expressive | ButtonGroup, FABMenu, LoadingIndicator, SplitButton, VerticalFloatingToolbar, spring animations with speed variants, 35 shape variations |
| Animations | AnimatedVisibility, infinite transitions, gesture-driven animations, bouncy springs, path-based motion |
| Custom Drawing | Canvas primitives, Bezier curves, linear gradients, bar charts, donut charts |

### Hardware and Services

| Module | Demonstrates |
|--------|-------------|
| Sensors | Accelerometer, gyroscope, magnetometer, light sensor, proximity sensor, step counter, sensor enumeration |
| Multimedia | CameraX preview and capture, MediaRecorder audio recording, MediaPlayer playback, MediaSession integration, CameraX extensions |
| Location | GPS provider, network provider, continuous location tracking, geofencing, coarse location (Android 16) |
| Bluetooth & NFC | BLE availability check, device scanning, GATT connection, NFC tag read/write, Android Beam deprecation notice |
| Wi-Fi & Network | Network callback monitoring, Wi-Fi connection info, TCP/UDP socket demonstrations, capability detection |

### Data and Storage

| Module | Demonstrates |
|--------|-------------|
| Local Storage | SharedPreferences, DataStore (Preferences), Room database (SQLite), internal/external file storage |
| KeyStore | Encrypted storage and cryptographic key management |

### System Integration

| Module | Demonstrates |
|--------|-------------|
| Notifications | Channel groups, basic notifications, actionable notifications, progress notifications, rich media (BigPictureStyle) |
| Background Tasks | WorkManager (one-time, periodic, chained), AlarmManager (exact, repeating), JobScheduler with constraints |
| Permissions | Runtime permission requests (camera, location, microphone, notifications), special permissions (system settings, overlay, battery optimization), rationale display |
| Accessibility | AccessibilityManager, TalkBack detection, content descriptions, touch target sizes, contrast ratios, font scaling |

### Android 16 Exclusive

| Module | Demonstrates |
|--------|-------------|
| Android 16 APIs | Privacy Sandbox overview, coarse location improvements, background activity start restrictions, foreground service type refinements, SDK_INT_FULL version checking, edge-to-edge enforcement, path traversal protection, photo picker updates |

## Design System

### Material 3 Expressive

This application implements the Material 3 Expressive design system introduced with Android 16:

- **Dynamic Color**: Wallpaper-based color extraction with automatic dark theme adaptation
- **Spring Animations**: Physics-based motion with three speed profiles (default, fast, slow)
- **Expressive Typography**: 30 font styles optimized for emotional connection and readability
- **Shape System**: 35 shape variations extending the baseline M3 shape tokens
- **Updated Components**: Enhanced buttons, progress indicators, navigation bars, app bars, menus, and sliders

### Dark Mode

Full dark theme support with:
- System theme following
- Dynamic color compatibility (Android 12+)
- Smooth theme transition animations
- Proper contrast ratios for accessibility

## Architecture

### MVVM Pattern

Each feature module follows the Model-View-ViewModel pattern:

- **Model**: Data layer with Room, DataStore, and system APIs
- **View**: Compose UI screens observing state flows
- **ViewModel**: State management with Kotlin Flow and coroutines

### Dependency Injection

Simple service locator pattern via `AppModule` object providing:
- Application context
- SharedPreferences instance
- DataStore instance
- Room database instance
- Repository instances

### Clean Architecture Layers

- **Presentation**: Compose screens and ViewModels
- **Domain**: Use cases and business logic (repository interfaces)
- **Data**: Repository implementations, data sources, database

## Permissions

The application declares the following permissions in its manifest:

### Normal Permissions
- INTERNET
- ACCESS_NETWORK_STATE
- ACCESS_WIFI_STATE
- VIBRATE
- WAKE_LOCK
- RECEIVE_BOOT_COMPLETED

### Dangerous Permissions (Runtime Request Required)
- ACCESS_COARSE_LOCATION
- ACCESS_FINE_LOCATION
- ACCESS_BACKGROUND_LOCATION
- BLUETOOTH_SCAN
- BLUETOOTH_CONNECT
- BLUETOOTH_ADVERTISE
- CAMERA
- RECORD_AUDIO
- READ_MEDIA_IMAGES
- READ_MEDIA_VIDEO
- READ_MEDIA_AUDIO
- POST_NOTIFICATIONS
- BODY_SENSORS
- ACTIVITY_RECOGNITION

### Special Permissions
- FOREGROUND_SERVICE (and types: camera, location, microphone, dataSync)
- SCHEDULE_EXACT_ALARM
- SYSTEM_ALERT_WINDOW
- NFC

## Build and Run

### Prerequisites
- Android Studio Ladybug or newer
- JDK 21
- Android SDK Platform 36
- Android SDK Build-Tools 36

### Building
```bash
./gradlew assembleDebug
```

### Running Tests
```bash
./gradlew test
./gradlew connectedAndroidTest
```

### Generating APK
```bash
./gradlew assembleRelease
```

## API Coverage

This application demonstrates the following Android API categories:

1. Application Framework (Activity, Service, BroadcastReceiver, ContentProvider)
2. User Interface (Compose layouts, M3 Expressive components, animations, canvas)
3. Hardware Access (sensors, camera, microphone, Bluetooth, NFC, Wi-Fi)
4. Location Services (GPS, network, geofencing)
5. Data Storage (SharedPreferences, DataStore, Room, file system, KeyStore)
6. System Integration (notifications, background tasks, permissions, accessibility)
7. Networking (connectivity monitoring, sockets)
8. Multimedia (camera, audio recording, media playback, MediaSession)
9. Android 16 Platform APIs (privacy sandbox, coarse location, FGS types)

## Target Audience

- Android developers learning platform capabilities
- Technical interview preparation
- API reference and quick prototyping
- Educational demonstrations
- Architecture pattern examples

## License

This project is provided for educational purposes.
