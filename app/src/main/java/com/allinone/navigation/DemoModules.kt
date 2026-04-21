package com.allinone.navigation

import kotlinx.serialization.Serializable

// Navigation routes using type-safe navigation
sealed class Route {
    @Serializable
    object Home

    @Serializable
    data class ModuleDetail(val moduleId: String, val moduleName: String)

    // Sub-routes for each module category
    object SubRoutes {
        const val NAVIGATION = "navigation"
        const val SERVICES = "services"
        const val BROADCAST = "broadcast"
        const val CONTENT_PROVIDER = "content_provider"
        const val UI_LAYOUTS = "ui_layouts"
        const val M3_EXPRESSIVE = "m3_expressive"
        const val ANIMATIONS = "animations"
        const val CUSTOM_DRAWING = "custom_drawing"
        const val SENSORS = "sensors"
        const val MULTIMEDIA = "multimedia"
        const val LOCATION = "location"
        const val BLUETOOTH_NFC = "bluetooth_nfc"
        const val WIFI_NETWORK = "wifi_network"
        const val STORAGE = "storage"
        const val KEYSTORE = "keystore"
        const val NOTIFICATIONS = "notifications"
        const val BACKGROUND = "background"
        const val PERMISSIONS = "permissions"
        const val ACCESSIBILITY = "accessibility"
        const val ANDROID16 = "android16"
    }
}

// Module definitions for the home screen
data class DemoModule(
    val id: String,
    val title: String,
    val description: String,
    val icon: String, // icon name
    val category: ModuleCategory,
    val route: String
)

enum class ModuleCategory(val displayName: String) {
    CORE("Core Framework"),
    UI("UI & Interaction"),
    HARDWARE("Hardware & Services"),
    DATA("Data & Storage"),
    SYSTEM("System Integration"),
    ANDROID16("Android 16 Exclusive")
}

val AllDemoModules = listOf(
    // Core Framework
    DemoModule(
        id = "navigation",
        title = "Activity & Navigation",
        description = "Activity lifecycle, launch modes, Compose Navigation",
        icon = "Navigation",
        category = ModuleCategory.CORE,
        route = Route.SubRoutes.NAVIGATION
    ),
    DemoModule(
        id = "services",
        title = "Services",
        description = "Foreground, background, and bound services",
        icon = "SettingsApplications",
        category = ModuleCategory.CORE,
        route = Route.SubRoutes.SERVICES
    ),
    DemoModule(
        id = "broadcast",
        title = "BroadcastReceiver",
        description = "Static/dynamic broadcast registration, system broadcasts",
        icon = "BroadcastOnPersonal",
        category = ModuleCategory.CORE,
        route = Route.SubRoutes.BROADCAST
    ),
    DemoModule(
        id = "content_provider",
        title = "ContentProvider",
        description = "Cross-application data sharing and queries",
        icon = "CloudSync",
        category = ModuleCategory.CORE,
        route = Route.SubRoutes.CONTENT_PROVIDER
    ),

    // UI & Interaction
    DemoModule(
        id = "ui_layouts",
        title = "UI & Layouts",
        description = "Column, Row, Box, LazyColumn, ConstraintLayout",
        icon = "Dashboard",
        category = ModuleCategory.UI,
        route = Route.SubRoutes.UI_LAYOUTS
    ),
    DemoModule(
        id = "m3_expressive",
        title = "M3 Expressive",
        description = "ButtonGroup, FABMenu, LoadingIndicator, SplitButton, Toolbars",
        icon = "Palette",
        category = ModuleCategory.UI,
        route = Route.SubRoutes.M3_EXPRESSIVE
    ),
    DemoModule(
        id = "animations",
        title = "Animations",
        description = "Shared element transitions, spring animations, gesture-driven animations",
        icon = "Animation",
        category = ModuleCategory.UI,
        route = Route.SubRoutes.ANIMATIONS
    ),
    DemoModule(
        id = "custom_drawing",
        title = "Custom Drawing",
        description = "Canvas drawing, Shape variations (35+ M3 Expressive shapes)",
        icon = "Brush",
        category = ModuleCategory.UI,
        route = Route.SubRoutes.CUSTOM_DRAWING
    ),

    // Hardware & Services
    DemoModule(
        id = "sensors",
        title = "Sensors",
        description = "Accelerometer, gyroscope, magnetometer, light, proximity, step counter",
        icon = "Sensors",
        category = ModuleCategory.HARDWARE,
        route = Route.SubRoutes.SENSORS
    ),
    DemoModule(
        id = "multimedia",
        title = "Multimedia",
        description = "Camera, microphone, audio/video playback, MediaSession",
        icon = "Multimedia",
        category = ModuleCategory.HARDWARE,
        route = Route.SubRoutes.MULTIMEDIA
    ),
    DemoModule(
        id = "location",
        title = "Location",
        description = "GPS, network location, geofencing, location updates",
        icon = "MyLocation",
        category = ModuleCategory.HARDWARE,
        route = Route.SubRoutes.LOCATION
    ),
    DemoModule(
        id = "bluetooth_nfc",
        title = "Bluetooth & NFC",
        description = "Bluetooth scan/connect/communicate, NFC tag read/write",
        icon = "Bluetooth",
        category = ModuleCategory.HARDWARE,
        route = Route.SubRoutes.BLUETOOTH_NFC
    ),
    DemoModule(
        id = "wifi_network",
        title = "Wi-Fi & Network",
        description = "Wi-Fi scan/management, network monitoring, socket communication",
        icon = "Wifi",
        category = ModuleCategory.HARDWARE,
        route = Route.SubRoutes.WIFI_NETWORK
    ),

    // Data & Storage
    DemoModule(
        id = "storage",
        title = "Local Storage",
        description = "SharedPreferences, DataStore, Room (SQLite), file storage",
        icon = "Storage",
        category = ModuleCategory.DATA,
        route = Route.SubRoutes.STORAGE
    ),
    DemoModule(
        id = "keystore",
        title = "KeyStore",
        description = "Encrypted storage and key management",
        icon = "Https",
        category = ModuleCategory.DATA,
        route = Route.SubRoutes.KEYSTORE
    ),

    // System Integration
    DemoModule(
        id = "notifications",
        title = "Notifications",
        description = "Basic, actionable, rich media notifications, channel grouping",
        icon = "Notifications",
        category = ModuleCategory.SYSTEM,
        route = Route.SubRoutes.NOTIFICATIONS
    ),
    DemoModule(
        id = "background",
        title = "Background Tasks",
        description = "WorkManager, AlarmManager, JobScheduler",
        icon = "Schedule",
        category = ModuleCategory.SYSTEM,
        route = Route.SubRoutes.BACKGROUND
    ),
    DemoModule(
        id = "permissions",
        title = "Permissions",
        description = "Runtime permissions, special permissions (overlay, system settings)",
        icon = "Security",
        category = ModuleCategory.SYSTEM,
        route = Route.SubRoutes.PERMISSIONS
    ),
    DemoModule(
        id = "accessibility",
        title = "Accessibility",
        description = "Accessibility services integration",
        icon = "Accessibility",
        category = ModuleCategory.SYSTEM,
        route = Route.SubRoutes.ACCESSIBILITY
    ),

    // Android 16 Exclusive
    DemoModule(
        id = "android16",
        title = "Android 16 APIs",
        description = "Privacy Sandbox, coarse location, background start restrictions",
        icon = "NewReleases",
        category = ModuleCategory.ANDROID16,
        route = Route.SubRoutes.ANDROID16
    )
)
