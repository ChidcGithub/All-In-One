package com.allinone.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.allinone.feature.android16.Android16Screen
import com.allinone.feature.connectivity.BluetoothNfcScreen
import com.allinone.feature.connectivity.WifiNetworkScreen
import com.allinone.feature.home.HomeScreen
import com.allinone.feature.home.HomeViewModel
import com.allinone.feature.multimedia.MultimediaScreen
import com.allinone.feature.permissions.PermissionsScreen
import com.allinone.feature.sensors.SensorsScreen
import com.allinone.feature.storage.StorageScreen
import com.allinone.feature.system.BroadcastScreen
import com.allinone.feature.system.ContentProviderScreen
import com.allinone.feature.system.NavigationScreen
import com.allinone.feature.system.ServicesScreen
import com.allinone.feature.ui.AnimationsScreen
import com.allinone.feature.ui.CustomDrawingScreen
import com.allinone.feature.ui.M3ExpressiveScreen
import com.allinone.feature.ui.UILayoutsScreen
import com.allinone.feature.system.NotificationScreen
import com.allinone.feature.background.BackgroundTasksScreen
import com.allinone.feature.system.LocationScreen

@Composable
fun AllInOneNavHost(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val homeViewModel: HomeViewModel = viewModel()

    Scaffold(modifier = modifier) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Home,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() }
        ) {
            // Home screen
            composable<Route.Home> {
                HomeScreen(
                    viewModel = homeViewModel,
                    isExpandedScreen = isExpandedScreen,
                    onModuleClick = { module ->
                        navController.navigate(Route.ModuleDetail(module.id, module.title))
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            // Module detail screen
            composable<Route.ModuleDetail> { backStackEntry ->
                val args = backStackEntry.toRoute<Route.ModuleDetail>()
                ModuleDetailScreen(
                    moduleId = args.moduleId,
                    moduleName = args.moduleName,
                    onNavigateBack = { navController.popBackStack() },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun ModuleDetailScreen(
    moduleId: String,
    moduleName: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (moduleId) {
        Route.SubRoutes.NAVIGATION -> NavigationScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.SERVICES -> ServicesScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.BROADCAST -> BroadcastScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.CONTENT_PROVIDER -> ContentProviderScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.UI_LAYOUTS -> UILayoutsScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.M3_EXPRESSIVE -> M3ExpressiveScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.ANIMATIONS -> AnimationsScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.CUSTOM_DRAWING -> CustomDrawingScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.SENSORS -> SensorsScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.MULTIMEDIA -> MultimediaScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.LOCATION -> LocationScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.BLUETOOTH_NFC -> BluetoothNfcScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.WIFI_NETWORK -> WifiNetworkScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.STORAGE -> StorageScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.KEYSTORE -> StorageScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.NOTIFICATIONS -> NotificationScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.BACKGROUND -> BackgroundTasksScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.PERMISSIONS -> PermissionsScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.ACCESSIBILITY -> AccessibilityScreen(onNavigateBack = onNavigateBack, modifier = modifier)
        Route.SubRoutes.ANDROID16 -> Android16Screen(onNavigateBack = onNavigateBack, modifier = modifier)
        else -> {
            // Fallback
            HomeScreen(
                viewModel = viewModel(),
                isExpandedScreen = false,
                onModuleClick = {},
                modifier = modifier
            )
        }
    }
}

// Placeholder for accessibility screen - will be defined in its module
@Composable
fun AccessibilityScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    com.allinone.feature.system.AccessibilityScreen(onNavigateBack = onNavigateBack, modifier = modifier)
}
