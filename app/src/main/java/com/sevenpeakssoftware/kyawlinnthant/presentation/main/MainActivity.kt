package com.sevenpeakssoftware.kyawlinnthant.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.sevenpeakssoftware.kyawlinnthant.app.CarsGraph
import com.sevenpeakssoftware.kyawlinnthant.app.theme.CarsTheme
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
            val navController = rememberNavController()
            CarsTheme(
                themePreference = themeStatus.value,
                isDynamicEnabled = dynamicStatus.value
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