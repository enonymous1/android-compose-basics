package com.enonymous1.composebasics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GreetingsUiState(
    val shouldShowOnboarding: Boolean = true,
    val expandedNames: Set<String> = emptySet(),
)

/**
 * Owns the greetings screen state across rotation and process death: whether
 * the onboarding screen is still showing, and which greeting cards are
 * expanded. The greeting names themselves are fixed, so they live here as a
 * single list instead of being rebuilt as a composable default argument.
 */
class GreetingsViewModel(
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(),
) : ViewModel() {

    companion object {
        private const val KEY_SHOULD_SHOW_ONBOARDING = "should_show_onboarding"
        private const val KEY_EXPANDED_NAMES = "expanded_names"
    }

    /** The fixed list of greeting names, "0" through "999". */
    val names: List<String> = List(1000) { "$it" }

    private val _uiState = MutableStateFlow(
        GreetingsUiState(
            shouldShowOnboarding = savedStateHandle[KEY_SHOULD_SHOW_ONBOARDING] ?: true,
            expandedNames = savedExpandedNames(),
        ),
    )
    val uiState: StateFlow<GreetingsUiState> = _uiState.asStateFlow()

    private fun savedExpandedNames(): Set<String> {
        val saved: ArrayList<String>? = savedStateHandle[KEY_EXPANDED_NAMES]
        return saved?.toSet().orEmpty()
    }

    /** The onboarding Continue button was tapped: show the greetings list. */
    fun onContinueClicked() {
        savedStateHandle[KEY_SHOULD_SHOW_ONBOARDING] = false
        _uiState.update { it.copy(shouldShowOnboarding = false) }
    }

    /** Toggle one greeting card's expanded content. */
    fun onToggleExpanded(name: String) {
        val expanded = _uiState.value.expandedNames.toMutableSet()
        if (!expanded.add(name)) {
            expanded.remove(name)
        }
        savedStateHandle[KEY_EXPANDED_NAMES] = ArrayList(expanded)
        _uiState.update { it.copy(expandedNames = expanded) }
    }
}
