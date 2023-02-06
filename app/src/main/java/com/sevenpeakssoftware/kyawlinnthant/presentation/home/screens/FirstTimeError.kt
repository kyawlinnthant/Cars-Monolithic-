package com.sevenpeakssoftware.kyawlinnthant.presentation.home.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sevenpeakssoftware.kyawlinnthant.R
import com.sevenpeakssoftware.kyawlinnthant.app.theme.CarsTheme
import com.sevenpeakssoftware.kyawlinnthant.presentation.home.HomeUiState

@Composable
fun FirstTimeErrorView(
    modifier: Modifier = Modifier,
    message: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().testTag("first_time_error"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            modifier = modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = onRetry) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Preview
@Composable
private fun FirstTimeErrorPreview() {
    CarsTheme {
        Surface {
            FirstTimeErrorView(message = "Can't access internet", onRetry = {})
        }
    }
}