package com.sevenpeakssoftware.kyawlinnthant.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.sevenpeakssoftware.kyawlinnthant.app.theme.CarsTheme
import com.sevenpeakssoftware.kyawlinnthant.presentation.home.HomeContent
import com.sevenpeakssoftware.kyawlinnthant.presentation.home.HomeUiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.shadows.ShadowLog

//before adding robolectric, these tests can be run and result as expected ,as usual.
//I am not sure what is wrong with this. May be dependency conflicting.
//Currently documentation says that Robolectric supports in compose.
//I research the issues and they mentioned to update the latest version. but I've tried all 4+ versions
//In my case, I can't do it right by time ( during code test process )
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out
    }

    private val hasCars get() = composeTestRule.onNodeWithTag("has_cars")
    private val firstTimeLoading get() = composeTestRule.onNodeWithTag("first_time_loading")
    private val firstTimeError get() = composeTestRule.onNodeWithTag("first_time_error")

    @Test
    fun firstTimeLoading() {
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
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.waitForIdle()

        firstTimeLoading.assertExists()
        firstTimeError.assertDoesNotExist()
        hasCars.assertDoesNotExist()

    }

    @Test
    fun firstTimeError() {
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
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.waitForIdle()

        firstTimeError.assertExists()
        firstTimeLoading.assertDoesNotExist()
        hasCars.assertDoesNotExist()

    }

    @Test
    fun hasCars() {
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
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.waitForIdle()

        hasCars.assertExists()
        firstTimeLoading.assertDoesNotExist()
        firstTimeError.assertDoesNotExist()

    }
}