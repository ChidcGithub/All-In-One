package com.allinone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowWidthSizeClass
import com.allinone.core.theme.AllInOneTheme
import com.allinone.navigation.AllInOneNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllInOneTheme {
                val adaptiveInfo = currentWindowAdaptiveInfo()
                val isExpanded = adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED
                AllInOneNavHost(
                    isExpandedScreen = isExpanded,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
