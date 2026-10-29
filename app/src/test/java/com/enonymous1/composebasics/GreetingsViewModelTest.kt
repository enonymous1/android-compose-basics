package com.enonymous1.composebasics

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GreetingsViewModelTest {

    @Test
    fun names_areOneThousandNumberedEntries() {
        val viewModel = GreetingsViewModel()
        assertEquals(1000, viewModel.names.size)
        assertEquals("0", viewModel.names.first())
        assertEquals("999", viewModel.names.last())
    }

    @Test
    fun initialState_showsOnboardingWithNothingExpanded() {
        val viewModel = GreetingsViewModel()
        assertTrue(viewModel.uiState.value.shouldShowOnboarding)
        assertTrue(viewModel.uiState.value.expandedNames.isEmpty())
    }

    @Test
    fun continueClicked_hidesOnboarding() {
        val viewModel = GreetingsViewModel()
        viewModel.onContinueClicked()
        assertFalse(viewModel.uiState.value.shouldShowOnboarding)
    }

    @Test
    fun toggleExpanded_addsThenRemovesTheName() {
        val viewModel = GreetingsViewModel()
        viewModel.onToggleExpanded("42")
        assertEquals(setOf("42"), viewModel.uiState.value.expandedNames)
        viewModel.onToggleExpanded("42")
        assertTrue(viewModel.uiState.value.expandedNames.isEmpty())
    }

    @Test
    fun toggleExpanded_keepsOtherExpandedNames() {
        val viewModel = GreetingsViewModel()
        viewModel.onToggleExpanded("1")
        viewModel.onToggleExpanded("2")
        assertEquals(setOf("1", "2"), viewModel.uiState.value.expandedNames)
    }

    @Test
    fun state_isRestoredFromSavedState() {
        val savedStateHandle = SavedStateHandle(
            initialState = mapOf(
                "should_show_onboarding" to false,
                "expanded_names" to arrayListOf("3", "7"),
            ),
        )
        val viewModel = GreetingsViewModel(savedStateHandle)
        assertFalse(viewModel.uiState.value.shouldShowOnboarding)
        assertEquals(setOf("3", "7"), viewModel.uiState.value.expandedNames)
    }

    @Test
    fun emptySavedState_restoresDefaults() {
        val viewModel = GreetingsViewModel(SavedStateHandle())
        assertTrue(viewModel.uiState.value.shouldShowOnboarding)
        assertTrue(viewModel.uiState.value.expandedNames.isEmpty())
    }
}
