package com.sevenpeakssoftware.kyawlinnthant.presentation.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.sevenpeakssoftware.kyawlinnthant.app.CarsGraph
import com.sevenpeakssoftware.kyawlinnthant.app.theme.AppThemeStatus
import com.sevenpeakssoftware.kyawlinnthant.app.theme.CarsTheme
import com.sevenpeakssoftware.kyawlinnthant.domain.model.ThemeType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vm by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val themeStatus = vm.uiTheme.collectAsState()
            val dynamicStatus = vm.uiDynamic.collectAsState()
            val is12AndAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            val isSystemDark = isSystemInDarkTheme()
            val navController = rememberNavController()

            val appThemeStatus = if (is12AndAbove && dynamicStatus.value) {
                when (themeStatus.value) {
                    ThemeType.DEFAULT -> if (isSystemDark) AppThemeStatus.DynamicDark else AppThemeStatus.DynamicLight
                    ThemeType.LIGHT -> AppThemeStatus.DynamicLight
                    ThemeType.DARK -> AppThemeStatus.DynamicDark
                }
            } else {
                when (themeStatus.value) {
                    ThemeType.DEFAULT -> if (isSystemDark) AppThemeStatus.Dark else AppThemeStatus.Light
                    ThemeType.LIGHT -> AppThemeStatus.Light
                    ThemeType.DARK -> AppThemeStatus.Dark
                }
            }

            CarsTheme(
                appThemeStatus = appThemeStatus
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    CarsGraph(navController = navController)
                }
            }
        }
    }
}