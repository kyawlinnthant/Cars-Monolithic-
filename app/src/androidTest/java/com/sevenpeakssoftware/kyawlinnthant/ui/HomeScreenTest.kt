package com.sevenpeakssoftware.kyawlinnthant.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.sevenpeakssoftware.kyawlinnthant.app.theme.CarsTheme
import com.sevenpeakssoftware.kyawlinnthant.domain.model.ThemeType
import com.sevenpeakssoftware.kyawlinnthant.presentation.home.HomeContent
import com.sevenpeakssoftware.kyawlinnthant.presentation.home.HomeUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun firstTimeLoadingTest() {
        composeTestRule.setContent {
            CarsTheme(themePreference = ThemeType.DEFAULT, isDynamicEnabled = true) {
                HomeContent(
                    uiState = HomeUiState.FirstTimeLoading,
                    listState = rememberLazyListState(),
                    onRetry = { },
                    onItemClicked = {})
            }
        }
        composeTestRule.onNodeWithTag("first_time_loading").assertExists()
        composeTestRule.onNodeWithTag("first_time_error").assertDoesNotExist()
        composeTestRule.onNodeWithTag("has_cars").assertDoesNotExist()
    }

    @Test
    fun firstTimeErrorTest() {
        composeTestRule.setContent {
            CarsTheme(themePreference = ThemeType.DEFAULT, isDynamicEnabled = true) {
                HomeContent(
                    uiState = HomeUiState.FirstTimeError(message = "error"),
                    listState = rememberLazyListState(),
                    onRetry = { },
                    onItemClicked = {})
            }
        }
        composeTestRule.onNodeWithTag("first_time_error").assertExists()
        composeTestRule.onNodeWithTag("first_time_loading").assertDoesNotExist()
        composeTestRule.onNodeWithTag("has_cars").assertDoesNotExist()
    }

    @Test
    fun hasNews() {
        composeTestRule.setContent {
            CarsTheme(themePreference = ThemeType.DEFAULT, isDynamicEnabled = true) {
                HomeContent(
                    uiState = HomeUiState.HasCars(cars = emptyList()),
                    listState = rememberLazyListState(),
                    onRetry = { },
                    onItemClicked = {})
            }
        }
        composeTestRule.onNodeWithTag("has_cars").assertExists()
        composeTestRule.onNodeWithTag("first_time_error").assertDoesNotExist()
        composeTestRule.onNodeWithTag("first_time_loading").assertDoesNotExist()
    }
}