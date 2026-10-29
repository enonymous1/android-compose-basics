package com.enonymous1.composebasics

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** Deterministic greetings-screen checks; no ViewModel or activity involved. */
@RunWith(AndroidJUnit4::class)
class GreetingsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun onboarding_showsWelcomeAndContinueInvokesCallback() {
        var continued = false
        composeRule.setContent {
            OnboardingScreen(onContinueClicked = { continued = true })
        }
        composeRule.onNodeWithTag("onboarding_screen").assertIsDisplayed()
        composeRule.onNodeWithText("Continue").assertIsDisplayed()
        composeRule.onNodeWithTag("continue_button").performClick()
        assertEquals(true, continued)
    }

    @Test
    fun myApp_showsOnboardingThenGreetings() {
        composeRule.setContent {
            MyApp(
                state = GreetingsUiState(shouldShowOnboarding = true),
                onContinueClicked = {},
                onToggleExpanded = {},
                names = listOf("0", "1"),
            )
        }
        composeRule.onNodeWithTag("onboarding_screen").assertIsDisplayed()

        composeRule.setContent {
            MyApp(
                state = GreetingsUiState(shouldShowOnboarding = false),
                onContinueClicked = {},
                onToggleExpanded = {},
                names = listOf("0", "1"),
            )
        }
        composeRule.onNodeWithTag("greetings_list").assertIsDisplayed()
        composeRule.onNodeWithText("0").assertIsDisplayed()
    }

    @Test
    fun greetings_expandButtonInvokesCallbackForThatName() {
        var toggled: String? = null
        composeRule.setContent {
            Greetings(
                names = listOf("0", "1"),
                expandedNames = emptySet(),
                onToggleExpanded = { toggled = it },
            )
        }
        composeRule.onNodeWithTag("expand_button_1").performClick()
        assertEquals("1", toggled)
    }

    @Test
    fun greetings_expandedCardShowsExpandedText() {
        composeRule.setContent {
            Greetings(
                names = listOf("0"),
                expandedNames = setOf("0"),
                onToggleExpanded = {},
            )
        }
        composeRule.onNodeWithTag("greeting_card_0").assertIsDisplayed()
        composeRule.onNodeWithTag("expanded_text_0").assertIsDisplayed()
    }

    @Test
    fun greetings_collapsedCardHidesExpandedText() {
        composeRule.setContent {
            Greetings(
                names = listOf("0"),
                expandedNames = emptySet(),
                onToggleExpanded = {},
            )
        }
        composeRule.onNodeWithTag("greeting_card_0").assertIsDisplayed()
        composeRule.onNodeWithTag("expanded_text_0").assertDoesNotExist()
    }
}
