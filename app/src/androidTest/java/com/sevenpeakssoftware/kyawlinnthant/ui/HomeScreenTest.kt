package com.sevenpeakssoftware.kyawlinnthant.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.sevenpeakssoftware.kyawlinnthant.app.theme.CarsTheme
import com.sevenpeakssoftware.kyawlinnthant.presentation.home.HomeContent
import com.sevenpeakssoftware.kyawlinnthant.presentation.home.HomeUiState
import org.junit.Rule
import org.junit.Test


class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val hasCars get() = composeTestRule.onNodeWithTag("has_cars")
    private val firstTimeLoading get() = composeTestRule.onNodeWithTag("first_time_loading")
    private val firstTimeError get() = composeTestRule.onNodeWithTag("first_time_error")

    @Test
    fun first_time_loading_state_shows_first_time_loading_screen() {
        composeTestRule.setContent {
            CarsTheme {
                HomeContent(
                    uiState = HomeUiState.FirstTimeLoading,
                    listState = rememberLazyListState(),
                    onRetry = { },
                    onItemClicked = { }
                )
            }
        }
        firstTimeLoading.assertExists()
        firstTimeError.assertDoesNotExist()
        hasCars.assertDoesNotExist()

    }

    @Test
    fun first_time_error_state_shows_first_time_error_screen() {
        // Start the app
        composeTestRule.setContent {
            CarsTheme {
                HomeContent(
                    uiState = HomeUiState.FirstTimeLoading,
                    listState = rememberLazyListState(),
                    onRetry = { },
                    onItemClicked = { }
                )
            }
        }
        firstTimeError.assertExists()
        firstTimeLoading.assertDoesNotExist()
        hasCars.assertDoesNotExist()

    }

    @Test
    fun has_cars_state_shows_has_cars_screen() {
        composeTestRule.setContent {
            CarsTheme {
                HomeContent(
                    uiState = HomeUiState.FirstTimeLoading,
                    listState = rememberLazyListState(),
                    onRetry = { },
                    onItemClicked = { }
                )
            }
        }
        hasCars.assertExists()
        firstTimeLoading.assertDoesNotExist()
        firstTimeError.assertDoesNotExist()

    }
}