package com.sevenpeakssoftware.kyawlinnthant.presentation.home.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.sevenpeakssoftware.kyawlinnthant.app.theme.CarsTheme

@Composable
fun FirstTimeLoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("first_time_loading"),
        contentAlignment = Alignment.Center

    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun FirstTimeLoadingPreview() {
    CarsTheme {
        Surface {
            FirstTimeLoadingView()
        }
    }
}